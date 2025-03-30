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
  const [message, setMessage] = useState<string | null>(null);

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
      setMessage("Error to create user");
    }
  }

  const handleCancel = () => {
    formik.resetForm();
  };

  return {
    formik,
    message,
    handleCancel,
  };
};
