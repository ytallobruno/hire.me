import axios from "axios";
import { getApiUrl } from "@/utils/environment";

const api = axios.create({
  baseURL: getApiUrl(process.env.NODE_ENV),
  headers: {
    "Content-Type": "application/json",
  },
});

export default api;
