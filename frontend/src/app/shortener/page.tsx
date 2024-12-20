"use client";
import { JSX, useState } from "react";
import DefaultButton from "@/app/_components/defaultButton";
import { shortenUrl } from "@/app/_actions/urlAction";
import Spinner from "@/app/_components/spinner";
import TextInput from "@/app/_components/textInput";
import axios from "axios";

const Shortener = () => {
  const [url, setUrl] = useState("");
  const [alias, setAlias] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState<JSX.Element | null>(null);
  const [messageType, setMessageType] = useState<"success" | "error" | "">("");

  const handleShorten = async () => {
    setLoading(true);
    setMessage(null);
    await wait(0.7)
    setMessageType("");
    try {
      const formattedUrl = /^https?:\/\//i.test(url) ? url : `http://${url}`;
      const response = await shortenUrl(formattedUrl, alias);
      setMessage(getSuccessMessage(alias, response.alias));
      setMessageType("success");
    } catch (error) {
      const errorMessage =
        axios.isAxiosError(error) && error.response?.status === 409
          ? "CUSTOM ALIAS ALREADY EXISTS"
          : "Error shortening URL, try again";
      setMessage(<span>{errorMessage}</span>);
      setMessageType("error");
    } finally {
      setLoading(false);
    }
  };

  const getSuccessMessage = (alias: string, responseAlias: string) => (
    <span>
      URL shortened successfully!
      {!alias && (
        <>
          <br />
          Here&#39;s your alias: <strong>{responseAlias}</strong>
        </>
      )}
    </span>
  );

  const wait = async (seconds: number) => {
    await new Promise((resolve) => setTimeout(resolve, seconds * 1000))
  }

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      handleShorten();
    }
  };

  return (
    <div className="flex flex-col items-center justify-center p-6 mt-20">
      <div className="w-full max-w-xs min-w-[220px] flex flex-col items-center space-y-4">
        <h1 className="text-2xl font-bold">Shorten URL</h1>
        <TextInput
          value={url}
          onChange={(e) => setUrl(e.target.value)}
          onKeyDown={handleKeyDown}
          placeholder="Enter the URL"
        />
        <TextInput
          value={alias}
          onChange={(e) => setAlias(e.target.value)}
          onKeyDown={handleKeyDown}
          placeholder="Enter a custom alias (optional)"
        />
        <div className="flex space-x-4">
          <DefaultButton
            label="Go Home"
            theme={"SECONDARY"}
            onClick={() => (window.location.href = "/")}
          />
          <DefaultButton
            label="Shorten"
            theme={"PRIMARY"}
            onClick={handleShorten}
          />
        </div>
        {loading && <Spinner />}
        {message && (
          <p
            className={`mt-4 ${messageType === "success" ? "text-green-500" : "text-red-500"}`}
          >
            {message}
          </p>
        )}
      </div>
    </div>
  );
};

export default Shortener;
