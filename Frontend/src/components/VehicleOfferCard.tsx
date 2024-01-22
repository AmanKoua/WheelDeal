interface Props {
  vehicleInfo: Object;
}

const VehicleOfferCard = ({ vehicleInfo }: Props) => {
  const processPrice = (priceNum: Number): String => {
    return priceNum.toLocaleString();
  };

  return (
    <div className="bg-gradient-to-b from-blue-100 to-blue-200 shadow-md hover:shadow-xl p-1 w-4/6 mt-2 ml-auto mr-auto">
      <div className="border-b border-gray-300 w-6/6 h-8 ml-auto mr-auto flex flex-row">
        {vehicleInfo.ownerDetails && (
          <div className="w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Name :</strong> {vehicleInfo.ownerDetails.firstName}{" "}
            {vehicleInfo.ownerDetails.lastName}
          </div>
        )}
        <div className=" w-1/3 h-max mt-auto mb-auto text-center">
          <strong>City :</strong> {vehicleInfo.cityName}
        </div>
        {vehicleInfo.ownerDetails && (
          <div className="w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Rating :</strong> {vehicleInfo.ownerDetails.avgRating}
          </div>
        )}
        {vehicleInfo.ownerDetails && (
          <div className=" w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Deal count :</strong> {vehicleInfo.ownerDetails.dealCount}
          </div>
        )}
      </div>
      <div className="border-b border-gray-300 w-6/6 h-8 mt-1 ml-auto mr-auto flex flex-row">
        {vehicleInfo.vehicle && (
          <div className=" w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Make :</strong> {vehicleInfo.vehicle.make}
          </div>
        )}
        {vehicleInfo.vehicle && (
          <div className=" w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Model :</strong> {vehicleInfo.vehicle.model}
          </div>
        )}
        {vehicleInfo.vehicle && (
          <div className=" w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Year :</strong> {vehicleInfo.vehicle.year}
          </div>
        )}
        {vehicleInfo.vehicle && (
          <div className=" w-1/3 h-max mt-auto mb-auto text-center">
            <strong>Color :</strong> {vehicleInfo.vehicle.color}
          </div>
        )}
      </div>
      <div className=" w-6/6 h-8 mt-1 ml-auto mr-auto flex flex-row">
        {vehicleInfo.vehicle && (
          <div className=" w-1/2 h-max mt-auto mb-auto text-center">
            <strong>Category :</strong>
            {processPrice(vehicleInfo.vehicle.category)}
          </div>
        )}
        {vehicleInfo.vehicle && (
          <div className=" w-1/2 h-max mt-auto mb-auto text-center">
            <strong>Price :</strong> $
            {processPrice(vehicleInfo.vehicle.defaultPrice)}
          </div>
        )}
        {vehicleInfo.vehicle && (
          <div className=" w-1/2 h-max mt-auto mb-auto text-center">
            <strong>Condition :</strong> {vehicleInfo.vehicle.condition}
          </div>
        )}
      </div>
    </div>
  );
};

export default VehicleOfferCard;
