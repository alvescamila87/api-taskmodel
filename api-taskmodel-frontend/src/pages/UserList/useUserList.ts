import { useEffect, useState } from "react";
import { User } from "../../services/userService/types";
import { userService } from "../../services/userService/userService";

const INITIAL_STATE_DATA = {
  data: [] as User[],
};

export const useUserList = () => {
  const [userData, setUserData] = useState(INITIAL_STATE_DATA);
  const [userEmail, setUserEmail] = useState("");
  const [loading, setLoading] = useState(false);
  const { findAll, findUserByEmail } = userService();

  async function fetchList() {
    setLoading(true);
    setTimeout(async () => {
      try {
        const response = await findAll();
        setUserData({
          data: response as User[],
        });
        setLoading(false);
      } catch (error) {
        console.error("Error to fetch users list:", error);
      }
    }, 500);
  }

  async function fetchUserByEmail(email: string) {
    console.log("Email: ", email);
    if (!email) {
      fetchList();
      return;
    }

    setLoading(true);
    try {
      const response = await findUserByEmail(email);
      console.log("USER BY ID: ", response);
      setUserData({
        data: response ?? {},
      });
      setLoading(false);
    } catch (error) {
      console.error("Error to fecth user by user email", error);
      throw error;
    }
  }

  const handleSearchUser = (email: string) => {
    fetchUserByEmail(email);
  };

  useEffect(() => {
    fetchList();
  }, []);

  return {
    userData,
    loading,
    userEmail,
    setUserEmail,
    setUserData,
    handleSearchUser,
  };
};
