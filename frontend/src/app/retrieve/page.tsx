"use client";

import { useEffect, useState } from "react";
import DefaultButton from "@/app/_components/defaultButton";
import Spinner from "@/app/_components/spinner";
import Modal from "@/app/_components/modal";
import TextInput from "@/app/_components/textInput";
import { getApiUrl } from "@/utils/environment";

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
    const url = `${getApiUrl(process.env.NODE_ENV)}/?alias=${alias}`;

    try {
      const response = await fetch(url, { redirect: "manual" });

      if (response.type === "opaqueredirect") {
        showSuccessModal(url);
        return;
      }

      const errorData = await response.json();
      handleModal("Error", errorData.description || "SHORTENED URL NOT FOUND");
    } catch {
      handleModal("Error", "Error retrieving URL, try again");
    } finally {
      setLoading(false);
    }
  };

  const showSuccessModal = (url: string) => {
    setRedirectTimer(3);
    handleModal("Success", "You are being redirected");

    const timer = setInterval(() => {
      setRedirectTimer((prev) => {
        if (prev === 1) {
          clearInterval(timer);
          window.open(url, "_blank");
          setShowModal(false);
        }
        return prev - 1;
      });
    }, 1000);
  };

  const handleModal = (title: string, message: string) => {
    setModalTitle(title);
    setModalMessage(message);
    setShowModal(true);
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
