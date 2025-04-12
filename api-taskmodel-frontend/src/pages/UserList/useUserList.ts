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
  const [editUser, setEditUser] = useState<User | null>(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [userToDelete, setUserToDelete] = useState<string | null>(null);
  const [openDeleteSnackbar, setOpenDeleteSnackbar] = useState(false);
  const [messageDelete, setMessageDelete] = useState<string>("");
  const [severityDelete, setSeverityDelete] = useState<"success" | "error">(
    "success"
  );

  const { findAll, findUserByEmail, deleteUser } = userService();

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

  async function confirmDeleteUserByEmail(email: string) {
    setLoading(true);
    try {
      const response = await deleteUser(email);

      if (response.status === 200) {
        setOpenDeleteSnackbar(true);
        setMessageDelete("User has been deleted");
        setSeverityDelete("success");
        handleCloseDeleteModal();
      } else if (response.status === 404) {
        setOpenDeleteSnackbar(true);
        setMessageDelete(response.data?.message || "User not found");
        setSeverityDelete("error");
        handleCloseDeleteModal();
      } else if (response.status === 409) {
        setOpenDeleteSnackbar(true);
        setMessageDelete(
          response.data?.message ||
            "User cannot be removed because he has tasks assigned to him"
        );
        setSeverityDelete("error");
        handleCloseDeleteModal();
      } else {
        console.error("Erro ao excluir usuário:", response);
        setOpenDeleteSnackbar(true);
        setMessageDelete(response.data?.message || "Error to delete user");
        setSeverityDelete("error");
        handleCloseDeleteModal();
      }
      setLoading(false);
    } catch (error: any) {
      console.error("Error to create user", error);
      setMessageDelete("Error to save user");
      setSeverityDelete("error");
      setOpenDeleteSnackbar(true);
      setLoading(false);
    }
  }

  const handleSearchUser = (email: string) => {
    fetchUserByEmail(email);
  };

  const handleOpenEditModal = (user: User) => {
    setEditUser(user);
    setIsEditModalOpen(true);
  };

  const handleCloseEditModal = () => {
    setEditUser(null);
    setIsEditModalOpen(false);
  };

  const handleEditSuccess = () => {
    fetchList();
    handleCloseEditModal();
  };

  const handleOpenDeleteModal = (email: string) => {
    setUserToDelete(email);
    setIsDeleteModalOpen(true);
  };

  const handleCloseDeleteModal = () => {
    setIsDeleteModalOpen(false);
    setUserToDelete(null);
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

    editUser,
    isEditModalOpen,
    handleOpenEditModal,
    handleCloseEditModal,
    handleEditSuccess,

    userToDelete,
    isDeleteModalOpen,
    handleOpenDeleteModal,
    handleCloseDeleteModal,
    openDeleteSnackbar,
    messageDelete,
    severityDelete,
    setOpenDeleteSnackbar,
    confirmDeleteUserByEmail,
  };
};
