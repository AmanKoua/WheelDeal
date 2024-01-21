import { useState, useEffect } from "react";
import VehicleCard from "../components/VehicleCard";

const MainHub = () => {
  const [category, setCategory] = useState("car");
  const [make, setMake] = useState("");
  const [model, setModel] = useState("");
  const [year, setYear] = useState("");
  const [cityName, setCityName] = useState("");
  const [citiesList, setCitiesList] = useState([]);
  const [minPrice, setMinPrice] = useState("");
  const [maxPrice, setMaxPrice] = useState("");
  const [vehicles, setVehicles] = useState([]);
  const [message, setMessage] = useState("");
  const [isError, setIsError] = useState<boolean>(false);

  useEffect(() => {
    if (citiesList.length > 0) {
      return;
    }

    const token = localStorage.getItem("token");

    if (!token || token.length == 0) {
      alert("cannot fetch cities without token!");
      return;
    }

    const fetchCities = async () => {
      const response = await fetch("http://localhost:8080/city/cities", {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        console.error("An error occured when fetching cities!");
        return;
      }

      const json = await response.json();
      setCitiesList(json);
    };

    fetchCities();
  }, [citiesList]);

  const search = async () => {
    const token = localStorage.getItem("token");

    if (!token || token.length == 0) {
      alert("cannot search when not logged in!");
      return;
    }

    if (!cityName) {
      alert("cannot search without a valid cityName!");
    }

    let fetchUrl = "http://localhost:8080/vehicle/filter";
    fetchUrl += `?cityName=${cityName}`;

    if (category) {
      fetchUrl += `&category=${category}`;
    }
    if (make) {
      fetchUrl += `&make=${make}`;
    }
    if (model) {
      fetchUrl += `&model=${model}`;
    }
    if (year) {
      fetchUrl += `&year=${year}`;
    }
    if (minPrice) {
      fetchUrl += `&min=${minPrice}`;
    }
    if (maxPrice) {
      fetchUrl += `&max=${maxPrice}`;
    }

    const response = await fetch(fetchUrl, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      method: "GET",
    });

    if (!response.ok) {
      console.error("Error filtering and fetching cities!");
      return;
    }

    let json;

    try {
      json = await response.json();
    } catch (e) {
      console.log(e);
      return;
    }

    setVehicles([]);
    setTimeout(() => {
      // timeout requried to avoid not updating bug!
      setVehicles(json);
    }, 10);
    // console.log(json);
  };

  const generateCityOptions = () => {
    return (
      <>
        {citiesList.map((item) => (
          <option value={item.name}>{item.name}</option>
        ))}
      </>
    );
  };

  const generateVehicleCards = () => {
    if (vehicles.length == 0) {
      return <></>;
    }
    return (
      <>
        {vehicles.map((item, idx) => (
          <VehicleCard key={idx} vehicle={item} citiesList={citiesList} />
        ))}
      </>
    );
  };

  return (
    <div className="flex flex-col" style={{ height: "100vh" }}>
      <div className="w-max h-max ml-auto mr-auto mt-4 border-b-2 font-semibold text-xl text-center">
        Find the right vehicle, just for you, using the filters below!
      </div>
      <div className="bg-gray-200 w-8/12 w-1/ h-max shadow-lg p-2 ml-auto mr-auto mt-4 flex flex-row justify-around">
        {/* ----------------------------------------------- */}
        <div className="w-28 h-20 flex flex-col ">
          <div className="w-full h-1/2 text-center pt-2 font-bold">
            Category
          </div>
          <div className="w-full h-1/2 text-center pt-2">
            <select
              className="w-3/4 text-center"
              onChange={(e) => {
                setCategory(e.target.value);
              }}
            >
              <option value="car">car</option>
              <option value="truck">truck</option>
              <option value="motorcycle">motorcycle</option>
              <option value="">any</option>
            </select>
          </div>
        </div>
        {/* ----------------------------------------------- */}
        <div className="w-28 h-20 flex flex-col ">
          <div className="w-full h-1/2 text-center pt-2 font-bold">Make</div>
          <div className="w-full h-1/2 text-center pt-2">
            <input
              className="w-3/4 pl-2"
              onChange={(e) => {
                setMake(e.target.value);
              }}
            />
          </div>
        </div>
        {/* ----------------------------------------------- */}
        <div className="w-28 h-20 flex flex-col ">
          <div className="w-full h-1/2 text-center pt-2 font-bold">Model</div>
          <div className="w-full h-1/2 text-center pt-2">
            <input
              className="w-3/4 pl-2"
              onChange={(e) => {
                setModel(e.target.value);
              }}
            />
          </div>
        </div>
        {/* ----------------------------------------------- */}
        <div className="w-28 h-20 flex flex-col ">
          <div className="w-full h-1/2 text-center pt-2 font-bold">Year</div>
          <div className="w-full h-1/2 text-center pt-2">
            <input
              className="w-3/4 pl-2"
              onChange={(e) => {
                setYear(e.target.value);
              }}
            />
          </div>
        </div>
        {/* ----------------------------------------------- */}
        <div className="w-28 h-20 flex flex-col ">
          <div className="w-full h-1/2 text-center pt-2 font-bold">
            City Name
          </div>
          <div className="w-3/4 h-1/2 text-center pt-2 ml-auto mr-auto">
            <select
              className="w-full"
              onChange={(e) => {
                setCityName(e.target.value);
              }}
            >
              <option value="">Select City</option>
              {generateCityOptions()}
            </select>
          </div>
        </div>
        {/* ----------------------------------------------- */}
        <div className="w-28 h-20 flex flex-col ">
          <div className="w-full h-1/2 text-center pt-2 font-bold">
            Min Price
          </div>
          <div className="w-full h-1/2 text-center pt-2">
            <input
              className="w-3/4 pl-2"
              onChange={(e) => {
                setMinPrice(e.target.value);
              }}
            />
          </div>
        </div>
        {/* ----------------------------------------------- */}
        <div className="w-28 h-20 flex flex-col ">
          <div className="w-full h-1/2 text-center pt-2 font-bold">
            Max Price
          </div>
          <div className="w-full h-1/2 text-center pt-2">
            <input
              className="w-3/4 pl-2"
              onChange={(e) => {
                setMaxPrice(e.target.value);
              }}
            />
          </div>
        </div>
      </div>
      <div
        className="bg-blue-800 text-blue-100 border border-gray-300 w-max p-2 h-max ml-auto mr-auto mt-12 text-center rounded-sm shadow-md hover:shadow-lg"
        onClick={() => {
          search();
        }}
      >
        Search
      </div>
      {generateVehicleCards()}
    </div>
  );
};

export default MainHub;
