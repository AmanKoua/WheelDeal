import { useState, useEffect } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import NavBar from "./components/NavBar";
import Footer from "./components/Footer";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import Home from "./pages/Home";
import MainHub from "./pages/MainHub";
import RegisterVehicle from "./pages/RegisterVehicle";
import Offer from "./pages/Offer";

function App() {
  const [vehicleInfo, setVehicleInfo] = useState<undefined | Object>({});

  useEffect(() => {
    if (vehicleInfo == undefined) {
      return;
    }
    console.log(vehicleInfo);
  }, [vehicleInfo]);

  return (
    <>
      <div className="flex flex-col">
        <BrowserRouter>
          <NavBar />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route
              path="/home"
              element={<MainHub setVehicleInfo={setVehicleInfo} />}
            />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/registerVehicle" element={<RegisterVehicle />} />
            <Route
              path="/offer"
              element={<Offer vehicleInfo={vehicleInfo} />}
            />
          </Routes>
        </BrowserRouter>
        <Footer />
      </div>
    </>
  );
}

export default App;
