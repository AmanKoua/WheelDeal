import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";

import UserVehicleCard from "../components/UserVehicleCard";

interface Props {
  specificVehicle: Object | undefined;
}

const Transactions = ({ specificVehicle }: Props) => {
  const [incommingOffers, setIncommingOffers] = useState([]);
  const [outgoingOffers, setOutgoingOffers] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    if (!specificVehicle) {
      return;
    }

    const token = localStorage.getItem("token");

    if (!token) {
      return;
    }

    const getIncommingTransactions = async () => {
      const response = await fetch(
        `http://localhost:8080/transaction/vehicle?id=${specificVehicle.id}&type=owner`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          method: "GET",
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
        setIncommingOffers(json);
        // console.log(json);
      } else {
        return;
      }
    };

    const getOutgoingTransactions = async () => {
      const response = await fetch(
        `http://localhost:8080/transaction/vehicle?id=${specificVehicle.id}&type=swapper`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          method: "GET",
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
        setOutgoingOffers(json);
        console.log(json);
      } else {
        return;
      }
    };

    getIncommingTransactions();
    getOutgoingTransactions();
  }, []);

  const generateIncommingTransactionCards = () => {
    return <>{}</>;
  };

  const generateOutgoingTransactionCards = () => {
    return (
      <>
        {outgoingOffers.map((item, idx) => (
          <div className="bg-red-200 w-4/6 h-20 mt-8 ml-auto mr-auto rounded-sm">
            aadga
          </div>
        ))}
      </>
    );
  };

  return (
    <div
      className="flex flex-col overflow-y-scroll hide-scrollbar"
      style={{ height: "100vh" }}
    >
      <h3 className="w-4/6 ml-auto mr-auto mt-5 text-center text-2xl font-semibold">
        Your Vehicle
      </h3>
      <div className="mt-1">
        <UserVehicleCard
          vehicleInfo={specificVehicle!}
          setSpecificVehicle={(val: Object | undefined) => {}}
        />
      </div>
      <h3 className="w-4/6 ml-auto mr-auto mt-5 text-center text-2xl font-semibold">
        Incomming Offers
      </h3>
      <h3 className="w-4/6 ml-auto mr-auto mt-5 text-center text-2xl font-semibold">
        Outgoing Offers
      </h3>
      {generateOutgoingTransactionCards()}
    </div>
  );
};

export default Transactions;
