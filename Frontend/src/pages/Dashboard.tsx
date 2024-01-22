import { useEffect, useState } from "react";
import UserVehicleCard from "../components/UserVehicleCard";

interface Props {
  setSpecificVehicle: (val: Object | undefined) => void;
}

const Dashboard = ({ setSpecificVehicle }: Props) => {
  const [userVehicles, setUserVehicles] = useState([]);
  const [userTransactionInfo, setUserTransactionInfo] = useState<
    Object | undefined
  >(undefined);

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
        console.log(json);
      }
    };

    const fetchUserTransactionInfo = async () => {
      const token = localStorage.getItem("token");
      const response = await fetch(
        "http://localhost:8080/transaction/updates",
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      let json;

      try {
        json = await response.json();
      } catch (e) {
        console.log(e);
        return;
      }

      if (response.ok) {
        setUserTransactionInfo(json);
        // console.log(json);
      }
    };

    fetchUserVehicles();
    fetchUserTransactionInfo();
  }, [userVehicles]);

  const generateUserVehicleCards = () => {
    if (userVehicles.length == 0 || !userTransactionInfo) {
      return <></>;
    } else {
      return (
        <>
          {userVehicles.map((item, idx) => (
            <>
              <UserVehicleCard
                vehicleInfo={item}
                setSpecificVehicle={setSpecificVehicle}
                key={idx}
              />
              <div className="bg-gradient-to-b from-blue-100 to-blue-200 shadow-md p-1 w-4/6 mt-2 ml-auto mr-auto flex flex-row justify-around">
                <div className="w-2/6 ">
                  Incomming : {userTransactionInfo[0].Incomming[`${item.id}`]}
                </div>
                <div className="w-2/6 ">
                  Outgoing : {userTransactionInfo[1].outgoing[`${item.id}`]}
                </div>
              </div>
            </>
          ))}
        </>
      );
    }
  };

  return (
    <div
      className="flex flex-col overflow-y-scroll hide-scrollbar"
      style={{ height: "100vh" }}
    >
      <h3 className=" w-max mt-7 mb-7 ml-auto mr-auto text-3xl font-semibold">
        My Vehicles
      </h3>
      {generateUserVehicleCards()}
    </div>
  );
};

export default Dashboard;
