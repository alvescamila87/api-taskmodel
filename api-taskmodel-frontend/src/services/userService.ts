import axios from "axios";

const BASE_PATH = "http://localhost:8080/user";

export const userService = () => {
  async function findAll() {
    try {
      const response = await axios.get(BASE_PATH);
      return response.data;
    } catch (error) {
      console.error("Erro ao buscar usuário:", error);
      throw error;
    }
  }

  return {
    findAll,
  };
};
