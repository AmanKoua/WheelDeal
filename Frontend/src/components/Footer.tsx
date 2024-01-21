import React from "react";

const Footer = () => {
  return (
    <div className="bg bg-gradient-to-b from-gray-200 via-gray-100 to bg-gray-100 w-full h-40 flex flex-row justify-around">
      <div className=" w-2/6 flex flex-col justify-around">
        <p className="w-max h-max text-3xl ml-auto mr-auto p-2">Wheel Deal</p>
        <p className="w-full h-max text-xl text-center ml-auto mr-auto p-2 font-extralight">
          The best vehicle trading platform on the web!
        </p>
      </div>
      <div
        className="bg-gray-500 h-5/6 mt-auto mb-auto"
        style={{ width: "1px" }}
      />
      <div className=" w-2/6 flex flex-col justify-around">
        <p className="w-max h-max ml-auto mr-auto border-black hover:border-b-2 font-extralight">
          Facebook
        </p>
        <p className="w-max h-max ml-auto mr-auto border-black hover:border-b-2 font-extralight">
          Instagram
        </p>
        <p className="w-max h-max ml-auto mr-auto border-black hover:border-b-2 font-extralight">
          LinkedIn
        </p>
        <p className="w-max h-max ml-auto mr-auto border-black hover:border-b-2 font-extralight">
          Twitter
        </p>
      </div>
      <div
        className="bg-gray-500 h-5/6 mt-auto mb-auto"
        style={{ width: "1px" }}
      />
      <div className=" w-2/6 flex flex-col justify-around">
        <p className="w-max h-max ml-auto mr-auto border-black hover:border-b-2 font-extralight">
          Contact Us
        </p>
        <p className="w-max h-max ml-auto mr-auto border-black hover:border-b-2 font-extralight">
          Careers
        </p>
        <p className="w-max h-max ml-auto mr-auto border-black hover:border-b-2 font-extralight">
          FAQ
        </p>
        <p className="w-max h-max ml-auto mr-auto border-black hover:border-b-2 font-extralight">
          Legal
        </p>
      </div>
    </div>
  );
};

export default Footer;
