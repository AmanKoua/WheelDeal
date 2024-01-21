import { useState, useEffect } from "react";
import Notification from "../components/Notification";

const Signup = () => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [age, setAge] = useState(0);
  const [gender, setGender] = useState("");
  const [cityName, setCityName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [password2, setPassword2] = useState("");
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

  const signup = async () => {
    if (password != password2) {
      setMessage("passwords do not match!");
      setIsError(true);
      return;
    }

    const response = await fetch("http://localhost:8080/auth/signup", {
      headers: {
        "Content-Type": "application/json",
      },
      method: "POST",
      body: JSON.stringify({
        firstName: firstName,
        lastName: lastName,
        age: age,
        gender: gender,
        cityName: cityName,
        phoneNumber: phoneNumber,
        email: email,
        password: password,
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
      setMessage(json.message);
      setIsError(false);
      localStorage.setItem("token", json.token);
    } else {
      setMessage(json.message);
      setIsError(true);
    }
  };

  return (
    <div
      className="flex flex-col h-max mt-20 mb-20" /*style={{ height: "100vh" }}*/
    >
      <div className="bg-gray-200 h-full w-1/4 pb-4 pt-4 rounded-md shadow-xl mt-auto mb-10 ml-auto mr-auto flex flex-col justify-around">
        <p className="w-max p-2 text-2xl font-semibold ml-auto mr-auto">
          Sign Up
        </p>
        <div className="w-5/6 h-4/6 ml-auto mr-auto flex flex-col justify-around">
          <p className="w-max p-2 font-semibold mr-auto">First Name</p>
          <input
            className="w-full h-7 mr-auto rounded-sm p-1"
            onChange={(e) => {
              setFirstName(e.target.value);
            }}
          />
          <p className="w-max p-2 font-semibold mr-auto">Last Name</p>
          <input
            className="w-full h-7 mr-auto rounded-sm p-1"
            onChange={(e) => {
              setLastName(e.target.value);
            }}
          />
          <p className="w-max p-2 font-semibold mr-auto">Age</p>
          <input
            className="w-full h-7 mr-auto rounded-sm p-1"
            onChange={(e) => {
              setAge(parseInt(e.target.value));
            }}
          />
          <p className="w-max p-2 font-semibold mr-auto">Gender (M / F)</p>
          <input
            className="w-full h-7 mr-auto rounded-sm p-1"
            onChange={(e) => {
              setGender(e.target.value);
            }}
          />
          <p className="w-max p-2 font-semibold mr-auto">City Name</p>
          <input
            className="w-full h-7 mr-auto rounded-sm p-1"
            onChange={(e) => {
              setCityName(e.target.value);
            }}
          />
          <p className="w-max p-2 font-semibold mr-auto">Phone number</p>
          <input
            className="w-full h-7 mr-auto rounded-sm p-1"
            onChange={(e) => {
              setPhoneNumber(e.target.value);
            }}
          />
          <p className="w-max p-2 font-semibold mr-auto">Email</p>
          <input
            className="w-full h-7 mr-auto rounded-sm p-1"
            onChange={(e) => {
              setEmail(e.target.value);
            }}
          />
          <p className="w-max p-2 font-semibold mr-auto">Password</p>
          <input
            type="password"
            className="w-full h-7 mr-auto rounded-sm p-1"
            onChange={(e) => {
              setPassword(e.target.value);
            }}
          />
          <p className="w-max p-2 font-semibold mr-auto">Password (repeat)</p>
          <input
            type="password"
            className="w-full h-7 mr-auto rounded-sm p-1"
            onChange={(e) => {
              setPassword2(e.target.value);
            }}
          />
        </div>
        <div
          className="w-full h-10 flex flex-row justify-around"
          onClick={() => {
            signup();
          }}
        >
          <div className="text-black h-3/4 mt-3 mb-auto pl-4 pr-4 rounded-sm shadow-md hover:shadow-lg flex">
            <p className="w-max h-max mt-auto mb-auto">Signup</p>
          </div>
        </div>
      </div>
      {message && <Notification isError={isError} message={message} />}
    </div>
  );
};

export default Signup;
