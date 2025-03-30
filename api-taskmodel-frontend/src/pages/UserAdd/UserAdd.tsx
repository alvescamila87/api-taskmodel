import { Cancel, Save } from "@mui/icons-material";
import { Alert, Box, Button, TextField, Typography } from "@mui/material";
import { useUserAdd } from "./useUserAdd";

export const UserAdd = () => {
  const { formik, message, handleCancel } = useUserAdd();
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

        {message && (
          <Alert
            sx={{ width: "100%", mb: 2 }}
            severity={message.includes("success") ? "success" : "error"}
          >
            {message}
          </Alert>
        )}

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
            onClick={() => handleCancel}
          >
            Cancel
          </Button>
          <Button
            type="submit"
            variant="contained"
            color="primary"
            startIcon={<Save />}
          >
            Save
          </Button>
        </Box>
      </Box>
    </form>
  );
};
