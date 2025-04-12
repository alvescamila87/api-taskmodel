import { Cancel, Save } from "@mui/icons-material";
import {
  Alert,
  Box,
  Button,
  Snackbar,
  TextField,
  Typography,
} from "@mui/material";
import { useUserForm, UseUserFormProps } from "./useUserForm";

export const UserForm = ({ initialValues, onSuccess }: UseUserFormProps) => {
  const {
    formik,

    openSnackbar,
    setOpenSnackbar,
    message,
    severity,
    //handleShowToastMessage,

    handleCancel,
  } = useUserForm({ initialValues, onSuccess });

  return (
    <form onSubmit={formik.handleSubmit}>
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          margin: 5,
          gap: 2,
          alignItems: "center",
          border: "2px solid #E0E0E0",
          borderRadius: "8px",
          boxShadow: "2px 2px 8px rgba(0, 0, 0, 0.05)",
          padding: "50px",
          width: { xs: "95%", sm: "80%", md: "50%" },
          mx: "auto",
        }}
      >
        <Typography variant="h5" fontWeight="bold" textAlign="center">
          Add user filling de form
        </Typography>

        <TextField
          label="Name"
          name="name"
          variant="outlined"
          value={formik.values.name}
          onChange={formik.handleChange}
          onBlur={formik.handleBlur}
          error={formik.touched.name ? Boolean(formik.errors.name) : false}
          helperText={formik.touched.name ? formik.errors.name : ""}
        />
        <TextField
          label="Email"
          name="email"
          type="email"
          variant="outlined"
          value={formik.values.email}
          onChange={formik.handleChange}
          onBlur={formik.handleBlur}
          error={formik.touched.email ? Boolean(formik.errors.email) : false}
          helperText={formik.touched.email ? formik.errors.email : ""}
        />
        <Box
          sx={{
            display: "flex",
            justifyContent: "flex-end",
            width: "100%",
            mt: 2,
            gap: 2,
          }}
        >
          <Button
            variant="outlined"
            color="info"
            startIcon={<Cancel />}
            onClick={() => handleCancel()}
          >
            Cancel
          </Button>
          <Button
            type="submit"
            variant="contained"
            color="primary"
            startIcon={<Save />}
            disabled={!formik.values.name || !formik.values.email}
            // onClick={() =>
            //   handleShowToastMessage("User saved successfully", "success")
            // }
          >
            Save
          </Button>
        </Box>
      </Box>
      <Snackbar
        open={openSnackbar}
        autoHideDuration={3000}
        onClose={() => setOpenSnackbar(false)}
        anchorOrigin={{ vertical: "top", horizontal: "right" }}
      >
        <Alert severity={severity} sx={{ width: "100%" }}>
          {message}
        </Alert>
      </Snackbar>
    </form>
  );
};
