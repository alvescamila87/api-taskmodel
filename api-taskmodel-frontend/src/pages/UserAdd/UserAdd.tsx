import { Box, TextField } from "@mui/material";
import { useUserAdd } from "./useUserAdd";

export const UserAdd = () => {
  const {} = useUserAdd();
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        gap: 2,
        alignItems: "center",
      }}
    >
      <TextField
        label="User Name"
        variant="outlined"
        value={userName}
        onChange={(e) => setUserName(e.target.value)}
      />
      <Button variant="contained" onClick={handleAddUser}>
        Add User
      </Button>
    </Box>
  );
};
