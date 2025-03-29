import { useEffect, useState } from "react";
import { userService } from "../../services/userService/userService";
import { User } from "../../services/userService/types";

const INITIAL_STATE_DATA = {
  data: [] as User[]
};

export const useUserList = () => {
  const [userData, setUserData] = useState(INITIAL_STATE_DATA);
  const [userEmail, setUserEmail] = useState("");
  const [loading, setLoading] = useState(false);
  const { findAll, findUserByEmail } = userService();

  async function fetchList() {
    setLoading(true);
    try {
      const response = await findAll();
      setUserData({
        data: response as User[],
      });
      setLoading(false)
    } catch (error) {
      console.error("Error to fetch users list:", error);
    }
  }

  async function fetchUserByEmail(email: string) {
    console.log("Email: ", email)
    if(!email) {
      fetchList();
      return;
    }

    setLoading(true);
    try { 
        const response = await findUserByEmail(email);
        setUserData(response ?? {});
        setLoading(false);
    } catch (error) {
        console.error("Error to fecth user by user email", error)
        throw error;
    }
}

const handleSearchUser = (email: string) =>  {
    fetchUserByEmail(email);
}

  useEffect(() => {
    fetchList();
  }, []);

  return {
    userData,
    loading,
    userEmail,
    setUserEmail,
    setUserData,
    handleSearchUser
  }
};
