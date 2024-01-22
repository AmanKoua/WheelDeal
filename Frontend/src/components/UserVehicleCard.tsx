import { useNavigate } from "react-router-dom";

interface Props {
  vehicleInfo: Object;
  setSpecificVehicle: (val: Object | undefined) => void;
}

const UserVehicleCard = ({ vehicleInfo, setSpecificVehicle }: Props) => {
  const navigate = useNavigate();

  const processPrice = (priceNum: Number): String => {
    return priceNum.toLocaleString();
  };

  return (
    <div
      className="bg-gradient-to-b from-blue-100 to-blue-200 shadow-md hover:shadow-xl p-1 w-4/6 mt-2 ml-auto mr-auto"
      onClick={() => {
        setSpecificVehicle(vehicleInfo);
        setTimeout(() => {
          navigate("/transactions");
        }, 500);
      }}
    >
      <div className="border-b border-gray-300 w-6/6 h-8 mt-1 ml-auto mr-auto flex flex-row">
        {vehicleInfo && (
          <div className=" w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Make :</strong> {vehicleInfo.make}
          </div>
        )}
        {vehicleInfo && (
          <div className=" w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Model :</strong> {vehicleInfo.model}
          </div>
        )}
        {vehicleInfo && (
          <div className=" w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Year :</strong> {vehicleInfo.year}
          </div>
        )}
        {vehicleInfo && (
          <div className=" w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Color :</strong> {vehicleInfo.color}
          </div>
        )}
      </div>
      <div className=" w-6/6 h-8 mt-1 ml-auto mr-auto flex flex-row">
        {vehicleInfo && (
          <div className=" w-1/2 h-max mt-auto mb-auto text-center">
            <strong>Category : </strong>
            {vehicleInfo.category}
          </div>
        )}
        {vehicleInfo && (
          <div className=" w-1/2 h-max mt-auto mb-auto text-center">
            <strong>Price :</strong> ${processPrice(vehicleInfo.defaultPrice)}
          </div>
        )}
        {vehicleInfo && (
          <div className=" w-1/2 h-max mt-auto mb-auto text-center">
            <strong>Condition :</strong> {vehicleInfo.condition}
          </div>
        )}
      </div>
    </div>
  );
};

export default UserVehicleCard;
