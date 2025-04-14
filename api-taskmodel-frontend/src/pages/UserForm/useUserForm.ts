import { useFormik } from "formik";
import { useEffect, useState } from "react";
import * as yup from "yup";
import { User } from "../../services/userService/types";
import { userService } from "../../services/userService/userService";

const INITIAL_VALUES_USER = {
  id: null,
  name: null,
  email: null,
};

const schema = yup.object().shape({
  name: yup.string().required("Name is required"),
  email: yup.string().email("Invalid email").required("Email is required"),
});

export interface UseUserFormProps {
  initialValues: User | null | undefined;
  onSuccess?: (message?: string) => void;
}

export const useUserForm = ({ initialValues, onSuccess }: UseUserFormProps) => {
  const { upsert } = userService();

  const [message, setMessage] = useState<string>("");
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [severity, setSeverity] = useState<"success" | "error">("success");

  const formik = useFormik<User>({
    initialValues: initialValues || INITIAL_VALUES_USER,
    validationSchema: schema,
    enableReinitialize: true,
    onSubmit: async (values, { resetForm }) => {
      await upsertUser(values, resetForm);
    },
  });

  async function upsertUser(values: User, resetForm: () => void) {
    try {
      //const response = await upsert(values);
      await upsert(values);
      setMessage(values.id ? "User update" : "User create");
      setSeverity("success");
      setOpenSnackbar(true);
      resetForm();
      if (onSuccess) onSuccess(initialValues ? "User updated" : "User created");
    } catch (error) {
      console.error("Error to create user", error);
      setMessage("Error to save user");
      setSeverity("error");
      setOpenSnackbar(true);
    }
  }

  useEffect(() => {
    if (initialValues) {
      formik.setValues(initialValues);
    }
  }, [initialValues]);

  const handleCancel = () => {
    formik.resetForm();
  };

  return {
    formik,

    openSnackbar,
    setOpenSnackbar,
    message,
    severity,
    //handleShowToastMessage,

    handleCancel,
  };
};
