import { Box, Button, TextField, Typography } from "@mui/material";
import { GridSearchIcon } from "@mui/x-data-grid";
import { useUserList } from "../UserList/useUserList";

export const UserSearch = () => {
  const { handleSearchUser, userEmail, setUserEmail } = useUserList();

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        width: "100%",
        mt: 4,
      }}
    >
      <Typography variant="h5" fontWeight="bold" textAlign="center" mb={2}>
        Search user
      </Typography>

      <Box
        sx={{
          width: { xs: "95%", sm: "90%", md: "80%" },
          p: 2,
          border: "2px solid #E0E0E0",
          borderRadius: "8px",
          boxShadow: "2px 2px 8px rgba(0, 0, 0, 0.05)",
          display: "flex",
          alignItems: "center",
          gap: 2,
          justifyContent: "space-between",
          flexWrap: "wrap",
        }}
      >
        <TextField
          id="email"
          label="User email"
          variant="outlined"
          value={userEmail}
          type="email"
          onChange={(e) => setUserEmail(e.target.value)}
        />
        <Button
          variant="contained"
          size="large"
          startIcon={<GridSearchIcon />}
          onClick={() => handleSearchUser(userEmail)}
        >
          Search
        </Button>
      </Box>
    </Box>
  );
};
