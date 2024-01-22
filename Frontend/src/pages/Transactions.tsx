import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";

import UserVehicleCard from "../components/UserVehicleCard";

interface Props {
  specificVehicle: Object | undefined;
}

const Transactions = ({ specificVehicle }: Props) => {
  const [vehicleDataMap, setVehicleDataMap] = useState<Map<Number, Object>>(
    new Map()
  );
  const [incommingOffers, setIncommingOffers] = useState([]);
  const [outgoingOffers, setOutgoingOffers] = useState([]);
  const navigate = useNavigate();

  const sleep = (ms: number) => {
    return new Promise((res, rej) => {
      setTimeout(() => {
        res(null);
      }, ms);
    });
  };

  useEffect(() => {
    if (vehicleDataMap.size == 0) {
      return;
    }

    // console.log("---", vehicleDataMap);
  }, [vehicleDataMap]);

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
        // console.log(json);
      } else {
        return;
      }
    };

    getIncommingTransactions();
    getOutgoingTransactions();
  }, []);

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      console.error("Cannot fetch vehicle data without token!");
      return;
    }

    if (incommingOffers.length == 0 && outgoingOffers.length == 0) {
      return;
    }

    const getVehicleData = async (id: number) => {
      const response = await fetch(`http://localhost:8080/vehicle/?id=${id}`, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      let json;

      try {
        json = await response.json();
        // console.log(json);
        vehicleDataMap.set(id, json[0]);
      } catch (e) {
        console.error(e);
        return;
      }
    };

    let setVehicleData = async () => {
      for (let i = 0; i < incommingOffers.length; i++) {
        await getVehicleData(incommingOffers[i].swapperVehicleId);
        await getVehicleData(incommingOffers[i].ownerVehicleId);
      }

      for (let i = 0; i < outgoingOffers.length; i++) {
        await getVehicleData(outgoingOffers[i].swapperVehicleId);
        await getVehicleData(outgoingOffers[i].ownerVehicleId);
      }

      let tempMap = new Map<Number, Object>();
      let vehicleMapKeys = vehicleDataMap.keys();
      let tempKey = vehicleMapKeys.next();

      while (tempKey.value != undefined) {
        // deep copy to circumvent state updating bug!
        tempMap.set(tempKey.value, vehicleDataMap.get(tempKey.value)!);
        tempKey = vehicleMapKeys.next();
      }

      setVehicleDataMap(tempMap);
    };

    setVehicleData();
  }, [incommingOffers, outgoingOffers]);

  const handleTransaction = async (id: number, isAccept: boolean) => {
    const token = localStorage.getItem("token");

    if (!token) {
      console.error("Cannot handle transaction because no token is found!");
      return;
    }

    let rating;

    if (isAccept) {
      rating = prompt("From 1-5, what would you rate the other user?");

      if (!rating || isNaN(parseFloat(rating))) {
        alert("Invalid user rating provided!");
        return;
      }
    }

    const url = isAccept
      ? `http://localhost:8080/transaction/accept?id=${id}&rating=${rating}`
      : `http://localhost:8080/transaction/reject?id=${id}`;

    const response = await fetch(url, {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    let json;

    try {
      json = await response.json();
      alert(json.message);
    } catch (e) {
      console.error(e);
    }
  };
  const generateIncommingTransactionCards = () => {
    if (incommingOffers.length == 0) {
      return (
        <h3 className="w-max ml-auto mr-auto mt-4 text-2xl border-b border-black font-light text-center">
          No Incomming Offers
        </h3>
      );
    }

    return (
      <>
        {incommingOffers.map((item, idx) => (
          <div className="bg-gradient-to-b from-blue-100 to-blue-200 shadow-md hover:shadow-xl p-1 w-4/6 mt-2 ml-auto mr-auto">
            <div className="border-b border-gray-300 w-6/6 h-8 mt-1 ml-auto mr-auto flex flex-row">
              <div className=" w-1/2 h-max mt-auto mb-auto text-center">
                <strong>Owner :</strong> {item.ownerEmail}
              </div>
              <div className=" w-1/2 h-max mt-auto mb-auto text-center">
                <strong>Swapper :</strong> {item.swapperEmail}
              </div>
            </div>
            <div className="border-b border-gray-300 w-6/6 h-8 mt-1 ml-auto mr-auto flex flex-row">
              <div className=" w-1/2 h-max mt-auto mb-auto text-center">
                <strong>Owner Agreement :</strong>{" "}
                {item.doesOwnerAgree.toString()}
              </div>
              <div className="w-1/2 h-max mt-auto mb-auto text-center">
                <strong>Swapper Agreement :</strong>{" "}
                {item.doesSwapperAgree.toString()}
              </div>
            </div>
            <div className="border-b border-gray-300 w-6/6 h-8 mt-1 ml-auto mr-auto flex flex-row">
              <div className=" w-1/2 h-max mt-auto mb-auto text-center">
                <strong>Swapper location :</strong> {item.swapperLocationOffer}
              </div>
              <div className=" w-1/2 h-max mt-auto mb-auto text-center">
                <strong>Owner offer :</strong> $
                {item.swapperPriceOffer.toLocaleString()}
              </div>
            </div>
            {vehicleDataMap.get(item.ownerVehicleId) != undefined && (
              <div className="border-b border-gray-300 w-6/6 h-8 mt-1 ml-auto mr-auto flex flex-row">
                <div className=" w-1/2 h-max mt-auto mb-auto text-center">
                  <strong>Vehicle : </strong>
                  {vehicleDataMap.get(item.swapperVehicleId).year}{" "}
                  {vehicleDataMap.get(item.swapperVehicleId).make}{" "}
                  {vehicleDataMap.get(item.swapperVehicleId).model}
                </div>
                <div className=" w-1/2 h-max mt-auto mb-auto text-center">
                  <strong>Condition :</strong>{" "}
                  {vehicleDataMap.get(item.swapperVehicleId).condition}
                </div>
              </div>
            )}
            <div className="border-b border-gray-300 w-6/6 h-8 mt-1 ml-auto mr-auto flex flex-row justify-around">
              <div
                className="bg-green-200 border-green-300 border-2 rounded-md w-1/3 h-full mt-auto mb-auto text-center"
                onClick={() => {
                  handleTransaction(item.id, true);
                }}
              >
                Accept Transaction
              </div>
              <div
                className="bg-red-200 border-red-300 border-2 rounded-md w-1/3 h-full mt-auto mb-auto text-center"
                onClick={() => {
                  handleTransaction(item.id, false);
                }}
              >
                Reject Transaction
              </div>
            </div>
          </div>
        ))}
      </>
    );
  };

  const generateOutgoingTransactionCards = () => {
    if (outgoingOffers.length == 0) {
      return (
        <h3 className="w-max ml-auto mr-auto mt-4 text-2xl border-b border-black font-light text-center">
          No Outgoing Offers
        </h3>
      );
    }

    return (
      <>
        {outgoingOffers.map((item, idx) => (
          <div className="bg-gradient-to-b from-blue-100 to-blue-200 shadow-md hover:shadow-xl p-1 w-4/6 mt-2 ml-auto mr-auto">
            <div className="border-b border-gray-300 w-6/6 h-8 mt-1 ml-auto mr-auto flex flex-row">
              <div className=" w-1/2 h-max mt-auto mb-auto text-center">
                <strong>Owner :</strong> {item.ownerEmail}
              </div>
              <div className=" w-1/2 h-max mt-auto mb-auto text-center">
                <strong>Swapper :</strong> {item.swapperEmail}
              </div>
            </div>
            <div className="border-b border-gray-300 w-6/6 h-8 mt-1 ml-auto mr-auto flex flex-row">
              <div className=" w-1/2 h-max mt-auto mb-auto text-center">
                <strong>Owner Agreement :</strong>{" "}
                {item.doesOwnerAgree.toString()}
              </div>
              <div className="w-1/2 h-max mt-auto mb-auto text-center">
                <strong>Swapper Agreement :</strong>{" "}
                {item.doesSwapperAgree.toString()}
              </div>
            </div>
            <div className="border-b border-gray-300 w-6/6 h-8 mt-1 ml-auto mr-auto flex flex-row">
              <div className=" w-1/2 h-max mt-auto mb-auto text-center">
                <strong>Owner location :</strong> {item.ownerLocationOffer}
              </div>
              <div className=" w-1/2 h-max mt-auto mb-auto text-center">
                <strong>Owner price :</strong> $
                {item.ownerPriceOffer.toLocaleString()}
              </div>
            </div>
            <div className="border-b border-gray-300 w-6/6 h-8 mt-1 ml-auto mr-auto flex flex-row">
              <div className=" w-1/2 h-max mt-auto mb-auto text-center">
                <strong>Swapper location :</strong> {item.swapperLocationOffer}
              </div>
              <div className=" w-1/2 h-max mt-auto mb-auto text-center">
                <strong>Owner offer :</strong> $
                {item.swapperPriceOffer.toLocaleString()}
              </div>
            </div>
            {vehicleDataMap.get(item.ownerVehicleId) != undefined && (
              <div className="border-b border-gray-300 w-6/6 h-8 mt-1 ml-auto mr-auto flex flex-row">
                <div className=" w-1/2 h-max mt-auto mb-auto text-center">
                  <strong>Vehicle : </strong>
                  {vehicleDataMap.get(item.ownerVehicleId).year}{" "}
                  {vehicleDataMap.get(item.ownerVehicleId).make}{" "}
                  {vehicleDataMap.get(item.ownerVehicleId).model}
                </div>
                <div className=" w-1/2 h-max mt-auto mb-auto text-center">
                  <strong>Condition :</strong>{" "}
                  {vehicleDataMap.get(item.ownerVehicleId).condition}
                </div>
              </div>
            )}
            <div className="border-b border-gray-300 w-6/6 h-8 mt-1 ml-auto mr-auto flex flex-row justify-around">
              <div
                className="bg-green-200 border-green-300 border-2 rounded-md w-1/3 h-full mt-auto mb-auto text-center"
                onClick={() => {
                  handleTransaction(item.id, true);
                }}
              >
                Accept Transaction
              </div>
              {/* <div
                className="bg-red-200 border-red-300 border-2 rounded-md w-1/3 h-full mt-auto mb-auto text-center"
                onClick={() => {
                  handleTransaction(item.id, false);
                }}
              >
                Reject Transaction
              </div> */}
            </div>
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
      {generateIncommingTransactionCards()}
      <h3 className="w-4/6 ml-auto mr-auto mt-5 text-center text-2xl font-semibold">
        Outgoing Offers
      </h3>
      {generateOutgoingTransactionCards()}
    </div>
  );
};

export default Transactions;
