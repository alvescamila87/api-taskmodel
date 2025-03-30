import { Box } from "@mui/material";
import { UserList } from "../UserList/UserList";
import { UserSearch } from "../UserSearch/UserSearch";

export const UserPage = () => {
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        width: "100%",
        minHeight: "100vh",
        paddingTop: { xs: "60px", sm: "80px" },
        px: { xs: 2, sm: 4 },
      }}
    >
      <UserSearch />
      <UserList />
    </Box>
  );
};
