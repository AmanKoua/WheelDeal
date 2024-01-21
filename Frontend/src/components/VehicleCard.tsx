import { useState, useEffect } from "react";

interface Props {
  vehicle: Object;
  citiesList: Object[];
}

const VehicleCard = ({ vehicle, citiesList }: Props) => {
  const [ownerDetails, setOwnerDetails] = useState([]);
  const [cityName, setCityName] = useState("");

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

  return (
    <div className="bg-red-200 w-3/4 mt-4 ml-auto mr-auto">{cityName}</div>
  );
};

export default VehicleCard;
