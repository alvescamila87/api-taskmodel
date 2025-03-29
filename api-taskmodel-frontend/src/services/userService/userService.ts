import axios from "axios";

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
      const response = await axios.get(`${BASE_PATH}/${email}`)
      return response.data ?? {};

    } catch (error) {
      console.log("Error to fetch user by email service", error);
      throw error;
    }
  }

  return {
    findAll,
    findUserByEmail
  };
};
