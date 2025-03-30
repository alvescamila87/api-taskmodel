import { useState } from "react";
import { User } from "../../services/userService/types";
import { userService } from "../../services/userService/userService";

export const useUserAdd = () => {
  const { upsert } = userService();

  const [userData, setUserData] = useState<User>(null);

  async function createUser() {
    try {
        const response = await upsert();
    }
  }

  return {
    userData,
  };
};
