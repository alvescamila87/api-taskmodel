import axios from "axios";

const BASE_PATH = "http://localhost:8080/user";

export const userService = () => {
  async function findAll(pageNumber: number, pageSize: number) {
    try {
      const response = await axios.get(BASE_PATH, {
        params: {
          pageNumber,
          pageSize,
        },
      });

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
