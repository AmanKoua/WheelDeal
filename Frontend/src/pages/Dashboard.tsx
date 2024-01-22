import { useEffect, useState } from "react";
import UserVehicleCard from "../components/UserVehicleCard";

const Dashboard = () => {
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
        // console.log(json);
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
        console.log(json);
      }
    };

    fetchUserVehicles();
    fetchUserTransactionInfo();
  }, [userVehicles]);

  const generateUserVehicleCards = () => {
    if (userVehicles.length == 0) {
      return <></>;
    } else {
      return (
        <>
          {userVehicles.map((item, idx) => (
            <UserVehicleCard vehicleInfo={item} key={idx} />
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
