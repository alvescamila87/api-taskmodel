import { useEffect, useState } from "react";
import { userService } from "../../services/userService/userService";
import { User } from "../../services/userService/types";

const INITIAL_STATE_DATA = {
  data: [] as User[]
};

export const useUserList = () => {
  const [userData, setUserData] = useState(INITIAL_STATE_DATA);

  const { findAll } = userService();

  async function fetchList() {

    try {
      const response = await findAll();
      //console.log("RESPONSE ", response)
      setUserData({
        data: response as User[],
      });
    } catch (error) {
      console.error("Error to fetch users list:", error);
    }
  }

  useEffect(() => {
    fetchList();
  }, []);

  return {
    userData,
    setUserData,
  }
};
