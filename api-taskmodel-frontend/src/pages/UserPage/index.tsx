import { Box } from "@mui/material";
import { UserSearch } from "../UserSearch/UserSearch";
import { UserList } from "../UserList/UserList";

export const UserPage = () => {
    return (
        <Box sx={{ display: "flex", flexDirection: "column", gap: 4, alignItems: "center" }}>
            <UserSearch />
            <UserList />
        </Box>
    );
};