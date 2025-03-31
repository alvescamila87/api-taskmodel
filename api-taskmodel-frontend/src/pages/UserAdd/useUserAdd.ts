import { useFormik } from "formik";
import { useState } from "react";
import * as yup from "yup";
import { User } from "../../services/userService/types";
import { userService } from "../../services/userService/userService";

const INITIAL_VALUES_USER = {
  name: "",
  email: "",
};

const schema = yup.object().shape({
  name: yup.string().required("Name is required"),
  email: yup.string().email("Invalid email").required("Email is required"),
});

export const useUserAdd = () => {
  const { upsert } = userService();

  const [userData, setUserData] = useState<User | null>(null);
  const [message, setMessage] = useState<string>("");
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [severity, setSeverity] = useState<"success" | "error">("success");

  const formik = useFormik({
    initialValues: INITIAL_VALUES_USER,
    validationSchema: schema,
    onSubmit: async (values, { resetForm }) => upserUser(values, resetForm),
  });

  async function upserUser(userData: User, resetForm: () => void) {
    try {
      const response = await upsert(userData);
      setUserData(response);
      setMessage("User has been save");
      resetForm();
    } catch (error) {
      console.error("Error to create user", error);
      handleShowToastMessage("Error to create user", "error");
    }
  }

  const handleShowToastMessage = (
    message: string,
    severity: "success" | "error"
  ) => {
    setMessage(message);
    setSeverity(severity);
    setOpenSnackbar(true);
  };

  const handleCancel = () => {
    formik.resetForm();
  };

  return {
    formik,

    openSnackbar,
    setOpenSnackbar,
    message,
    severity,
    handleShowToastMessage,

    handleCancel,
  };
};
