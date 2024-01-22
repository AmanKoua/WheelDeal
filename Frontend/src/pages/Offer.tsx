import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import VehicleCard from "../components/VehicleCard";
import VehicleOfferCard from "../components/VehicleOfferCard";
import UserVehicleCard from "../components/UserVehicleCard";

import "../invisibleScrollbar.css";

interface Props {
  vehicleInfo: Object | undefined;
}

const Offer = ({ vehicleInfo }: Props) => {
  const [userVehicles, setUserVehicles] = useState([]);
  const [userVehicleCounter, setUserVehicleCounter] = useState(0);
  const navigate = useNavigate();

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

      console.log(json);

      if (response.ok) {
        setUserVehicles(json);
        console.log(json);
      }
    };

    fetchUserVehicles();
  }, [userVehicles]);

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
      </div>
    );
  }
};

export default Offer;
