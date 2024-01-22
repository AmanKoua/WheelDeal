import { useState, useEffect } from "react";

import Notification from "../components/Notification";
import "../invisibleScrollbar.css";

const RegisterVehicle = () => {
  const [category, setCategory] = useState("car");
  const [make, setMake] = useState("");
  const [model, setModel] = useState("");
  const [year, setYear] = useState("");
  const [color, setColor] = useState("");
  const [price, setPrice] = useState("");
  const [condition, setCondition] = useState("");
  const [message, setMessage] = useState("");
  const [isError, setIsError] = useState<boolean>(false);

  useEffect(() => {
    if (!message) {
      return;
    }

    const timeout = setTimeout(() => {
      setMessage("");
    }, 3000);

    return () => {
      clearTimeout(timeout);
    };
  }, [message]);

  const registerVehicle = async () => {
    const token = localStorage.getItem("token");

    if (!token) {
      console.error("Cannot register vehicle while not logged in!");
      return;
    }

    const response = await fetch("http://localhost:8080/vehicle", {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      method: "POST",
      body: JSON.stringify({
        category: category,
        make: make,
        model: model,
        year: year,
        color: color,
        defaultPrice: price,
        condition: condition,
      }),
    });

    let json;

    try {
      json = await response.json();
    } catch (e) {
      console.log(e);
      return;
    }

    if (response.ok) {
      console.log("Vehicle posted successfull!");
      setIsError(false);
    } else {
      console.error("error posting vehicle");
      setIsError(true);
    }

    setMessage(json.message);
  };

  return (
    <div
      className="flex flex-col pb-10 overflow-y-scroll hide-scrollbar"
      style={{ height: "100vh" }}
    >
      <h3 className="w-max p-2 ml-auto mr-auto mt-10 text-3xl font-semibold">
        Register Vehicle
      </h3>
      <div className="bg-gray-200 h-max w-1/4 pb-4 pt-4 rounded-md shadow-xl mt-4 mb-auto ml-auto mr-auto flex flex-col justify-around">
        <div className=" w-5/6 h-1/6 ml-auto mr-auto mt-5 flex flex-col justify-around">
          <p className="w-max p-2 font-semibold mr-auto">Category</p>
          <select
            className="w-full h-7 mr-auto rounded-sm p-1"
            onChange={(e) => {
              setCategory(e.target.value);
            }}
          >
            <option value="car">car</option>
            <option value="truck">truck</option>
            <option value="motorcycle">motorcycle</option>
          </select>
        </div>
        <div className=" w-5/6 h-1/6 ml-auto mr-auto mt-5 flex flex-col justify-around">
          <p className="w-max p-2 font-semibold mr-auto">Make</p>
          <input
            type="text"
            className="pl-2"
            onChange={(e) => {
              setMake(e.target.value);
            }}
          />
        </div>
        <div className=" w-5/6 h-1/6 ml-auto mr-auto mt-5 flex flex-col justify-around">
          <p className="w-max p-2 font-semibold mr-auto">Model</p>
          <input
            type="text"
            className="pl-2"
            onChange={(e) => {
              setModel(e.target.value);
            }}
          />
        </div>
        <div className=" w-5/6 h-1/6 ml-auto mr-auto mt-5 flex flex-col justify-around">
          <p className="w-max p-2 font-semibold mr-auto">Year</p>
          <input
            type="text"
            className="pl-2"
            onChange={(e) => {
              setYear(e.target.value);
            }}
          />
        </div>
        <div className=" w-5/6 h-1/6 ml-auto mr-auto mt-5 flex flex-col justify-around">
          <p className="w-max p-2 font-semibold mr-auto">Color</p>
          <input
            type="text"
            className="pl-2"
            onChange={(e) => {
              setColor(e.target.value);
            }}
          />
        </div>
        <div className=" w-5/6 h-1/6 ml-auto mr-auto mt-5 flex flex-col justify-around">
          <p className="w-max p-2 font-semibold mr-auto">Price</p>
          <input
            type="text"
            className="pl-2"
            onChange={(e) => {
              setPrice(e.target.value);
            }}
          />
        </div>
        <div className=" w-5/6 h-1/6 ml-auto mr-auto mt-5 flex flex-col justify-around">
          <p className="w-max p-2 font-semibold mr-auto">Condition (1-5)</p>
          <input
            type="text"
            className="pl-2"
            onChange={(e) => {
              setCondition(e.target.value);
            }}
          />
        </div>
      </div>
      <div
        className="bg-blue-800 text-blue-100 border border-gray-300 w-max p-2 h-max ml-auto mr-auto mt-12 text-center rounded-sm shadow-md hover:shadow-lg"
        onClick={() => {
          registerVehicle();
        }}
      >
        Register Vehicle
      </div>
      {message && (
        <div className="w-full h-max mt-10">
          <Notification isError={isError} message={message} />
        </div>
      )}
    </div>
  );
};

export default RegisterVehicle;
