import { useState, useEffect } from "react";

import Notification from "../components/Notification";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
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

  const login = async (login: String, password: String) => {
    const response = await fetch("http://localhost:8080/auth/login", {
      headers: {
        "Content-Type": "application/json",
      },
      method: "POST",
      body: JSON.stringify({
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
    <div className="flex flex-col" style={{ height: "100vh" }}>
      <div className="bg-gray-200 h-2/4 w-1/4 pb-4 pt-4 rounded-md shadow-xl mt-auto mb-auto ml-auto mr-auto flex flex-col justify-around">
        <p className="w-max p-2 text-2xl font-semibold ml-auto mr-auto">
          Log In
        </p>
        <div className="w-5/6 h-4/6 ml-auto mr-auto flex flex-col justify-around">
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
        </div>
        <div
          className="w-full h-10 flex flex-row justify-around"
          onClick={() => {
            login(email, password);
          }}
        >
          <div className="text-black h-3/4 mt-auto mb-auto pl-4 pr-4 rounded-sm shadow-md hover:shadow-lg flex">
            <p className="w-max h-max mt-auto mb-auto">Login</p>
          </div>
        </div>
      </div>
      {message && <Notification isError={isError} message={message} />}
    </div>
  );
};

export default Login;
