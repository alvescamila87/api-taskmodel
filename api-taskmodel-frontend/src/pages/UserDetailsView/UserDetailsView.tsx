import { Box, Typography } from "@mui/material";
import { User } from "../../services/userService/types";

interface UserDetailsViewProps {
  user: User | null;
}

export const UserDetailsView: React.FC<UserDetailsViewProps> = ({ user }) => {
  if (!user) {
    return <Typography>No user selected.</Typography>;
  }

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        margin: 5,
        gap: 2,
        alignItems: "flex-start",
        border: "2px solid #E0E0E0",
        borderRadius: "8px",
        boxShadow: "2px 2px 8px rgba(0, 0, 0, 0.05)",
        padding: "30px",
        width: { xs: "95%", sm: "80%", md: "50%" },
        mx: "auto",
      }}
    >
      <Typography variant="h6" gutterBottom>
        User Details
      </Typography>
      <Typography variant="subtitle1">
        <strong>Name:</strong> {user.name}
      </Typography>
      <Typography variant="subtitle1">
        <strong>Email:</strong> {user.email}
      </Typography>
    </Box>
  );
};
