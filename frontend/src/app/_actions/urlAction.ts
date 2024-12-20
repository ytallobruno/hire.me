"use client";

import api from "@/services/axiosInstance";
import {
  PopularUrlResponse,
  ShortenUrlResponse,
} from "@/interfaces/urlInterface";

export async function shortenUrl(
  url: string,
  alias?: string,
): Promise<ShortenUrlResponse> {
  try {
    const endpoint = alias
      ? `/shortener?url=${url}&customAlias=${alias}`
      : `/shortener?url=${url}`;
    const response = await api.put<ShortenUrlResponse>(endpoint);
    return response.data;
  } catch (error) {
    console.error("Error shortening URL: ", error);
    throw error;
  }
}

//
// @TODO: Fix cors error while redirect
//
export async function retrieveUrl(alias: string): Promise<string> {
  try {
    const response = await api.get(`/?alias=${alias}`, {
      maxRedirects: 0,
    });
    return response.headers["location"];
  } catch (error) {
    console.error("Error retrieving URL: ", error);
    throw error;
  }
}

export async function getPopularUrls(): Promise<PopularUrlResponse[]> {
  try {
    const response = await api.get<PopularUrlResponse[]>("/popular");
    return response.data;
  } catch (error) {
    console.error("Error fetching popular URLs: ", error);
    throw error;
  }
}
