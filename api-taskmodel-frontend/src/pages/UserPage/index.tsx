import { Box } from "@mui/material";
import { UserList } from "../UserList/UserList";
import { UserSearch } from "../UserSearch/UserSearch";

export const UserPage = () => {
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        gap: 4,
        alignItems: "center",
        width: { xs: "100%", sm: "80%", md: "60%" },
      }}
    >
      <UserSearch />
      <UserList />
    </Box>
  );
};
