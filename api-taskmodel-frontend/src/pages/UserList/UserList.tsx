import { Delete, Edit, Visibility } from "@mui/icons-material";
import {
  Alert,
  Box,
  Button,
  CircularProgress,
  IconButton,
  Modal,
  Paper,
  Snackbar,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from "@mui/material";
import { User } from "../../services/userService/types";
import { UserDetailsView } from "../UserDetailsView/UserDetailsView";
import { UserForm } from "../UserForm/UserForm";
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
  const {
    userData,
    loading,

    viewUser,
    isViewModalOpen,
    handleOpenViewModal,
    handleCloseViewModal,

    editUser,
    isEditModalOpen,
    handleOpenEditModal,
    handleCloseEditModal,
    handleEditSuccess,

    userToDelete,
    isDeleteModalOpen,
    handleOpenDeleteModal,
    handleCloseDeleteModal,
    openDeleteSnackbar,
    messageDelete,
    severityDelete,
    setOpenDeleteSnackbar,
    confirmDeleteUserByEmail,
  } = useUserList();

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
                  ID
                </TableCell>
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
                    <TableCell>{user.id}</TableCell>
                    <TableCell>{user.name}</TableCell>
                    <TableCell>{user.email}</TableCell>
                    <TableCell align="center">
                      <IconButton
                        color="primary"
                        onClick={() => handleOpenViewModal(user)}
                      >
                        <Visibility />
                      </IconButton>
                      <IconButton
                        color="secondary"
                        onClick={() => handleOpenEditModal(user)}
                      >
                        <Edit />
                      </IconButton>
                      <IconButton
                        color="error"
                        onClick={() => handleOpenDeleteModal(user.email!)}
                      >
                        <Delete />
                      </IconButton>
                    </TableCell>
                  </TableRow>
                ))
              ) : (
                <TableRow>
                  <TableCell colSpan={3} align="center">
                    There is no user found.
                  </TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>
        )}
      </TableContainer>

      <Modal open={isViewModalOpen} onClose={handleCloseViewModal}>
        <Box
          sx={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            bgcolor: "background.paper",
            boxShadow: 24,
            borderRadius: 2,
            p: 4,
            width: { xs: "90%", sm: "70%", md: "50%" },
          }}
        >
          {viewUser && <UserDetailsView user={viewUser} />}
          <Box sx={{ mt: 2, display: "flex", justifyContent: "flex-end" }}>
            <Button onClick={handleCloseViewModal} color="primary">
              Close
            </Button>
          </Box>
        </Box>
      </Modal>

      <Modal open={isEditModalOpen} onClose={handleCloseEditModal}>
        <Box
          sx={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            bgcolor: "background.paper",
            boxShadow: 24,
            borderRadius: 2,
            p: 4,
            width: { xs: "90%", sm: "70%", md: "50%" },
          }}
        >
          <UserForm initialValues={editUser} onSuccess={handleEditSuccess} />
          <Box sx={{ mt: 2, display: "flex", justifyContent: "flex-end" }}>
            <Button onClick={handleCloseEditModal} color="primary">
              Close
            </Button>
          </Box>
        </Box>
      </Modal>

      <Snackbar
        open={openDeleteSnackbar}
        autoHideDuration={3000}
        onClose={() => setOpenDeleteSnackbar(false)}
        anchorOrigin={{ vertical: "top", horizontal: "right" }}
      >
        <Alert
          onClose={() => setOpenDeleteSnackbar(false)}
          severity={severityDelete}
          sx={{ width: "100%" }}
        >
          {messageDelete}
        </Alert>
      </Snackbar>

      <Modal
        open={isDeleteModalOpen}
        onClose={handleCloseDeleteModal}
        aria-labelledby="delete-confirmation-title"
        aria-describedby="delete-confirmation-description"
      >
        <Box
          sx={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            bgcolor: "background.paper",
            boxShadow: 24,
            borderRadius: 2,
            p: 4,
          }}
        >
          <Typography
            id="delete-confirmation-title"
            variant="h6"
            component="h2"
          >
            Confirm delete
          </Typography>
          <Typography id="delete-confirmation-description" sx={{ mt: 2 }}>
            Are you sure you want to delete: <strong>{userToDelete}</strong>?
          </Typography>
          <Box
            sx={{ mt: 2, display: "flex", justifyContent: "flex-end", gap: 2 }}
          >
            <Button onClick={handleCloseDeleteModal} color="primary">
              Cancel
            </Button>
            <Button
              onClick={() => {
                if (userToDelete) {
                  confirmDeleteUserByEmail(userToDelete);
                }
              }}
              color="error"
              autoFocus
              disabled={loading}
            >
              Delete
              {loading && <CircularProgress size={20} sx={{ ml: 1 }} />}
            </Button>
          </Box>
        </Box>
      </Modal>
    </Box>
  );
};
