"use client";
import { useState, useEffect } from "react";
import DefaultButton from "@/app/_components/defaultButton";
import { retrieveUrl } from "@/app/_actions/urlAction";
import Spinner from "@/app/_components/spinner";
import Modal from "@/app/_components/modal";
import TextInput from "@/app/_components/textInput";
import axios from "axios";

const Retrieve = () => {
  const [alias, setAlias] = useState("");
  const [loading, setLoading] = useState(false);
  const [modalTitle, setModalTitle] = useState("");
  const [modalMessage, setModalMessage] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [redirectTimer, setRedirectTimer] = useState(3);

  useEffect(() => {
    if (!showModal) {
      setRedirectTimer(3);
    }
  }, [showModal]);

  const handleRetrieve = async () => {
    setLoading(true);
    try {
      const retrievedData = await retrieveUrl(alias);
      setModalTitle("Success");
      setModalMessage(`You are being redirected to ${retrievedData}`);
      setShowModal(true);
      const timer = setInterval(() => {
        setRedirectTimer((prev) => {
          if (prev === 1) {
            clearInterval(timer);
            window.open(retrievedData, "_blank");
            setShowModal(false);
          }
          return prev - 1;
        });
      }, 1000);
    } catch (error) {
      setModalTitle("Error");
      if (axios.isAxiosError(error) && error.response?.status === 404) {
        setModalMessage("SHORTENED URL NOT FOUND");
      } else {
        setModalMessage("Error retrieving URL, try again");
      }
      setShowModal(true);
    } finally {
      setLoading(false);
    }
  };

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      handleRetrieve();
    }
  };

  return (
    <div className="flex flex-col items-center justify-center p-6 mt-20">
      <div className="w-full max-w-xs min-w-[220px] flex flex-col items-center space-y-4">
        <h1 className="text-2xl font-bold">Retrieve URL</h1>
        <TextInput
          value={alias}
          onChange={(e) => setAlias(e.target.value)}
          onKeyDown={handleKeyDown}
          placeholder="Enter the URL"
        />
        <div className="flex space-x-4">
          <DefaultButton
            label="Go Home"
            theme={"SECONDARY"}
            onClick={() => (window.location.href = "/")}
          />
          <DefaultButton
            label="Retrieve"
            theme={"PRIMARY"}
            onClick={handleRetrieve}
          />
        </div>
        {loading && <Spinner />}
        {showModal && (
          <Modal
            title={modalTitle}
            message={modalMessage}
            onClose={() => setShowModal(false)}
            timer={redirectTimer}
          />
        )}
      </div>
    </div>
  );
};

export default Retrieve;