import { Delete, Edit, Visibility } from "@mui/icons-material";
import {
  Box,
  CircularProgress,
  IconButton,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from "@mui/material";
import { User } from "../../services/userService/types";
import { useUserList } from "./useUserList";

// mockar dados
// function createData(
//   id: number,
//   name: string,
//   email: string,
// ) {
//   return { id, name, email};
// }

// const rows = [
//   createData(1, 'Zebedeu Abraão', "zebedeu@gmail.com"),
//   createData(2, 'Madalena Abraão', "madalena@gmail.com"),
//   createData(3, 'João Batista', "joao@gmail.com"),
// ];

export const UserList = () => {
  const { userData, loading } = useUserList();

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
      {/* Título da lista */}
      <Typography variant="h5" fontWeight="bold" textAlign="center" mb={2}>
        User list
      </Typography>

      <TableContainer
        component={Paper}
        sx={{
          width: { xs: "95%", sm: "90%", md: "80%" },
          p: { xs: 1, sm: 2, md: 3 },
          border: "2px solid #E0E0E0",
          borderRadius: "8px",
          boxShadow: "2px 2px 8px rgba(0, 0, 0, 0.05)",
        }}
      >
        {loading ? (
          <CircularProgress sx={{ display: "block", margin: "auto", mt: 3 }} />
        ) : (
          <Table>
            <TableHead>
              <TableRow>
                <TableCell sx={{ fontWeight: "bold", textAlign: "center" }}>
                  Name
                </TableCell>
                <TableCell sx={{ fontWeight: "bold", textAlign: "center" }}>
                  E-mail
                </TableCell>
                <TableCell sx={{ fontWeight: "bold", textAlign: "center" }}>
                  Actions
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {userData?.data?.length > 0 ? (
                userData?.data?.map((user: User) => (
                  <TableRow key={user.email}>
                    <TableCell>{user.name}</TableCell>
                    <TableCell>{user.email}</TableCell>
                    <TableCell align="center">
                      <IconButton color="primary">
                        <Visibility />
                      </IconButton>
                      <IconButton color="secondary">
                        <Edit />
                      </IconButton>
                      <IconButton color="error">
                        <Delete />
                      </IconButton>
                    </TableCell>
                  </TableRow>
                ))
              ) : (
                <TableRow>
                  <TableCell colSpan={3} align="center">
                    Nenhum usuário encontrado.
                  </TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>
        )}
      </TableContainer>
    </Box>
  );
};
