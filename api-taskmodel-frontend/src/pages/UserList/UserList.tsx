import {
  CircularProgress,
  IconButton,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
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
    <TableContainer
      component={Paper}
      sx={{
        width: "100%",
        margin: "auto",
        mt: 5,
        p: 2,
      }}
    >
      {loading ? (
        <CircularProgress sx={{ display: "block", margin: "auto", mt: 3 }} />
      ) : (
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Name</TableCell>
              <TableCell>E-mail</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {userData?.data?.length > 0 ? (
              userData?.data?.map((user: User) => (
                <TableRow key={user.email}>
                  <TableCell>{user.name}</TableCell>
                  <TableCell>{user.email}</TableCell>
                  <TableCell align="center">
                    <IconButton
                      color="primary"
                      //onClick={() => handleView(user)}
                    >
                      <Visibility />
                    </IconButton>
                    <IconButton
                      color="secondary"
                      //onClick={() => handleEdit(user)}
                    >
                      <Edit />
                    </IconButton>
                    <IconButton
                      color="error"
                      //onClick={() => handleDelete(user)}
                    >
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
  );
};
