import { useState, useEffect } from "react";

interface Props {
  vehicle: Object;
  citiesList: Object[];
  setVehicleInfo: (val: Object) => void;
}

const VehicleCard = ({ vehicle, citiesList, setVehicleInfo }: Props) => {
  const [ownerDetails, setOwnerDetails] = useState<Object | undefined>(
    undefined
  );
  const [cityName, setCityName] = useState("");

  useEffect(() => {
    if (!vehicle || ownerDetails != undefined) {
      return;
    }

    const getOwnerDetails = async () => {
      const token = localStorage.getItem("token");
      const response = await fetch(
        "http://localhost:8080/user?email=" + vehicle.ownerEmail,
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) {
        console.error("An error occured when fetching vehicle owner details!");
        return;
      }

      const json = await response.json();
      setOwnerDetails(json);
    };

    getOwnerDetails();
  }, [ownerDetails]);

  useEffect(() => {
    if (cityName) {
      return;
    }

    for (let i = 0; i < citiesList.length; i++) {
      if (vehicle.cityId == citiesList[i].id) {
        setCityName(citiesList[i].name);
        break;
      }
    }
  }, []);

  const processPrice = (priceNum: Number): String => {
    return priceNum.toLocaleString();
  };

  // console.log(vehicle);

  return (
    <div
      className="bg-gradient-to-b from-blue-100 to-blue-200 shadow-md hover:shadow-xl p-1 w-4/6 mt-8 ml-auto mr-auto"
      onClick={() => {
        setVehicleInfo({ vehicle, ownerDetails, cityName: cityName });
      }}
    >
      <div className="border-b border-gray-300 w-6/6 h-8 ml-auto mr-auto flex flex-row">
        {ownerDetails && (
          <div className="w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Name :</strong> {ownerDetails.firstName}{" "}
            {ownerDetails.lastName}
          </div>
        )}
        <div className=" w-1/3 h-max mt-auto mb-auto text-center">
          <strong>City :</strong> {cityName}
        </div>
        {ownerDetails && (
          <div className="w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Rating :</strong> {ownerDetails.avgRating}
          </div>
        )}
        {ownerDetails && (
          <div className=" w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Deal count :</strong> {ownerDetails.dealCount}
          </div>
        )}
      </div>
      <div className="border-b border-gray-300 w-6/6 h-8 mt-1 ml-auto mr-auto flex flex-row">
        {vehicle && (
          <div className=" w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Make :</strong> {vehicle.make}
          </div>
        )}
        {vehicle && (
          <div className=" w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Model :</strong> {vehicle.model}
          </div>
        )}
        {vehicle && (
          <div className=" w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Year :</strong> {vehicle.year}
          </div>
        )}
        {vehicle && (
          <div className=" w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Color :</strong> {vehicle.color}
          </div>
        )}
      </div>
      <div className=" w-6/6 h-8 mt-1 ml-auto mr-auto flex flex-row">
        {vehicle && (
          <div className=" w-1/2 h-max mt-auto mb-auto text-center">
            <strong>Category :</strong> {processPrice(vehicle.category)}
          </div>
        )}
        {vehicle && (
          <div className=" w-1/2 h-max mt-auto mb-auto text-center">
            <strong>Price :</strong> ${processPrice(vehicle.defaultPrice)}
          </div>
        )}
        {vehicle && (
          <div className=" w-1/2 h-max mt-auto mb-auto text-center">
            <strong>Condition :</strong> {vehicle.condition}
          </div>
        )}
      </div>
    </div>
  );
};

export default VehicleCard;
