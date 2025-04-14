import axios from "axios";
import { User } from "./types";

const BASE_PATH = "http://localhost:8080/user";

export const userService = () => {
  async function findAll() {
    try {
      const response = await axios.get(BASE_PATH);
      return response.data;
    } catch (error) {
      console.error("Error to fetch user service:", error);
      throw error;
    }
  }

  async function findUserByEmail(email: string) {
    try {
      const response = await axios.get(`${BASE_PATH}/${email}`);
      return response.data ?? {};
    } catch (error) {
      console.log("Error to fetch user by email service", error);
      throw error;
    }
  }

  async function upsert(data: User) {
    try {
      if (data?.id && data?.email) {
        const response = await axios.put(`${BASE_PATH}/${data.email}`, data);
        return response.data ?? {};
      } else {
        const response = await axios.post(BASE_PATH, data);
        return response.data ?? {};
      }
    } catch (error) {
      console.log("Error to upsert user service", error);
      throw error;
    }
  }

  async function deleteUser(email: string) {
    try {
      return await axios.delete(`${BASE_PATH}/${email}`);
    } catch (error) {
      console.log("Error to fetch user by email service", error);
      throw error;
    }
  }

  return {
    findAll,
    findUserByEmail,
    upsert,
    deleteUser,
  };
};
