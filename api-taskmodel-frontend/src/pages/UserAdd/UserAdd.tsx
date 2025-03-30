import { Box, Button, TextField } from "@mui/material";
import { GridSaveAltIcon } from "@mui/x-data-grid";
import { useUserAdd } from "./useUserAdd";

export const UserAdd = () => {
  const { userData } = useUserAdd();
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        margin: 5,
        gap: 2,
        alignItems: "center",
      }}
    >
      <TextField
        label="Name"
        variant="outlined"
        value={userData?.name}
        required
        //onChange={(e) => setUserName(e.target.value)}
      />
      <TextField
        label="User Name"
        variant="outlined"
        value={userData?.email}
        type="email"
        required
        //onChange={(e) => setUserName(e.target.value)}
      />
      <Button
        variant="contained"
        onClick={console.log}
        startIcon={<GridSaveAltIcon />}
      >
        Save
      </Button>
    </Box>
  );
};
