import { useEffect, useState } from "react";
import { userService } from "../services/userService";

const INITIAL_STATE_DATA = {
  data: [],
  totalItems: 0,
  currentPage: 1,
  pageSize: 10,
};

export const UserPage = () => {
  const [userData, setUserData] = useState(INITIAL_STATE_DATA);
  const { findAll } = userService();

  async function fetchList() {
    const { currentPage, pageSize } = userData;

    try {
      const response = await findAll(currentPage, pageSize);
      setUserData({
        ...userData,
        data: response.data,
        totalItems: response.totalItems,
      });
    } catch (error) {
      console.error("Erro ao buscar usuários:", error);
    }
  }

  useEffect(() => {
    fetchList();
  }),
    [userData.currentPage, userData.pageSize];
};
