import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import VehicleCard from "../components/VehicleCard";
import VehicleOfferCard from "../components/VehicleOfferCard";
import UserVehicleCard from "../components/UserVehicleCard";
import Notification from "../components/Notification";

import "../invisibleScrollbar.css";

interface Props {
  vehicleInfo: Object | undefined;
}

const Offer = ({ vehicleInfo }: Props) => {
  //   let exampleVehicleInfo = {
  //     vehicle: {
  //       id: 4,
  //       isAvailable: true,
  //       ownerEmail: "susanpeters@gmail.com",
  //       category: "motorcycle",
  //       make: "Harley",
  //       model: "Davidson",
  //       year: 1998,
  //       color: "Black",
  //       cityId: 2,
  //       defaultPrice: 85750,
  //       condition: 4,
  //     },
  //     ownerDetails: {
  //       id: 2,
  //       firstName: "Susan",
  //       middleName: null,
  //       lastName: "Peters",
  //       cityId: 2,
  //       phoneNumber: "1234567890",
  //       email: "susanpeters@gmail.com",
  //       dealCount: 0,
  //       avgRating: 0,
  //     },
  //     cityName: "Ann Arbor",
  //   };

  const [userMoneyOffer, setUserMoneyOffer] = useState(0);
  const [userLocationOffer, setUserLocationOffer] = useState("");
  const [userVehicles, setUserVehicles] = useState([]);
  const [userVehicleCounter, setUserVehicleCounter] = useState(0);
  const [message, setMessage] = useState("");
  const [isError, setIsError] = useState<boolean>(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (!message) {
      return;
    }

    const timeout = setTimeout(() => {
      setMessage("");
    }, 3000);

    return () => {
      clearTimeout(timeout);
    };
  }, [message]);

  useEffect(() => {
    if (!vehicleInfo) {
      navigate("/home");
    }
  }, []);

  useEffect(() => {
    if (userVehicles.length > 0) {
      return;
    }

    const fetchUserVehicles = async () => {
      const token = localStorage.getItem("token");
      const response = await fetch("http://localhost:8080/vehicle/myvehicles", {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      let json;

      try {
        json = await response.json();
      } catch (e) {
        console.log(e);
        return;
      }

      if (response.ok) {
        setUserVehicles(json);
        // console.log(json);
      }
    };

    fetchUserVehicles();
  }, [userVehicles]);

  const makeOffer = async () => {
    const token = localStorage.getItem("token");

    if (!token) {
      console.error("Cannot make a vehicle offer without being logged in!");
      return;
    }

    const response = await fetch("http://localhost:8080/transaction", {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      method: "POST",
      body: JSON.stringify({
        ownerVehicleId: vehicleInfo.vehicle.id,
        swapperVehicleId: userVehicles[userVehicleCounter].id,
        ownerEmail: vehicleInfo.vehicle.ownerEmail,
        swapperPriceOffer: userMoneyOffer,
        swapperLocationOffer: userLocationOffer,
      }),
    });

    let json;

    try {
      json = await response.json();
      //   console.log(json);
    } catch (e) {
      console.error(e);
      return;
    }

    if (response.ok) {
      setMessage(json.message);
      setIsError(false);
    } else {
      setMessage(json.message);
      setIsError(true);
    }
  };

  const generateUserVehicleCard = () => {
    if (userVehicles.length == 0) {
      return <></>;
    } else {
      return <UserVehicleCard vehicleInfo={userVehicles[userVehicleCounter]} />;
    }
  };

  if (vehicleInfo == undefined) {
    return (
      <div
        className="flex flex-col overflow-y-scroll hide-scrollbar"
        style={{ height: "100vh" }}
      >
        Vehicle info is undefined!
      </div>
    );
  } else {
    return (
      <div
        className="flex flex-col overflow-y-scroll hide-scrollbar"
        style={{ height: "100vh" }}
      >
        <h3 className=" w-4/6 ml-auto mr-auto mt-5 text-center text-2xl font-semibold">
          Other's Vehicle
        </h3>
        <VehicleOfferCard vehicleInfo={vehicleInfo} />
        <h3 className="w-4/6 ml-auto mr-auto mt-5 text-center text-2xl font-semibold">
          Your Vehicles
        </h3>
        {generateUserVehicleCard()}
        <div className="bg-gradient-to-b from-blue-100 to-blue-200 shadow-lg w-4/6 h-10 mt-10 ml-auto mr-auto flex flex-row justify-around">
          <h3 className=" h-max mt-auto mb-auto text-lg">
            Additional Monetary Offer
          </h3>
          <input
            className="h-3/4 mt-auto mb-auto pl-1 rounded-md"
            value={userMoneyOffer}
            onChange={(e) => {
              let val = parseInt(e.target.value);

              if (isNaN(val)) {
                val = 0;
              }

              setUserMoneyOffer(val);
            }}
          />
        </div>
        <div className="bg-gradient-to-b from-blue-100 to-blue-200 shadow-lg w-4/6 h-10 mt-10 ml-auto mr-auto flex flex-row justify-around">
          <h3 className=" h-max mt-auto mb-auto text-lg">Proposed Location</h3>
          <input
            className="h-3/4 mt-auto mb-auto pl-1 rounded-md"
            value={userLocationOffer}
            onChange={(e) => {
              setUserLocationOffer(e.target.value);
            }}
          />
        </div>
        <div className="shadow-md w-28 ml-auto mr-auto mt-10 flex flex-row justify-around">
          <p
            className="text-xl font-bold h-max hover:font-bold"
            onClick={() => {
              if (userVehicleCounter == 0) {
                return;
              }

              setUserVehicleCounter(userVehicleCounter - 1);
            }}
          >
            {"<"}
          </p>
          <p className="text-xl font-bold h-max">{userVehicleCounter}</p>
          <p
            className="text-xl font-bold h-max hover:font-bold"
            onClick={() => {
              if (userVehicleCounter == userVehicles.length - 1) {
                return;
              }

              setUserVehicleCounter(userVehicleCounter + 1);
            }}
          >
            {">"}
          </p>
        </div>
        <div
          className="bg-blue-800 text-blue-100 border border-gray-300 w-max p-2 h-max ml-auto mr-auto mt-12 text-center rounded-sm shadow-md hover:shadow-lg"
          onClick={() => {
            makeOffer();
          }}
        >
          Make Offer
        </div>
        {message && (
          <div className=" w-full h-max mt-5 mb-10">
            <Notification isError={isError} message={message} />
          </div>
        )}
      </div>
    );
  }
};

export default Offer;
