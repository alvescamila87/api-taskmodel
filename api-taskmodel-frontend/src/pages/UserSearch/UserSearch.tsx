import { Box, Button, TextField } from "@mui/material";
import { GridSearchIcon } from "@mui/x-data-grid";
import { useUserList } from "../UserList/useUserList";

export const UserSearch = () => {
  const { handleSearchUser, userEmail, setUserEmail } = useUserList();

  return (
    <Box
      sx={{
        margin: "auto",
        width: 800,
        p: 2,
        border: "1px solid grey",
        display: "flex",
        alignItems: "center",
        gap: 2,
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
        size="small"
        startIcon={<GridSearchIcon />}
        onClick={() => handleSearchUser(userEmail)}
      >
        Search
      </Button>
    </Box>
  );
};
