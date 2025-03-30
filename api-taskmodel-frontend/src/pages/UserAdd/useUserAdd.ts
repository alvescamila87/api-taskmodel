import { useState } from "react";
import { User } from "../../services/userService/types";
import { userService } from "../../services/userService/userService";

export const useUserAdd = () => {
  const { upsert } = userService();

  const [userData, setUserData] = useState<User | null>(null);

  async function createUser(user: User) {
    try {
      const response = await upsert(user);
      setUserData(response);
    } catch (error) {
      console.error("Error to create user", error);
      throw error;
    }
  }

  return {
    userData,
  };
};
