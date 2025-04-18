import { AppBar, Box, Button, Toolbar, Typography } from "@mui/material";
import { Link } from "react-router-dom";

export const Menu = () => {
  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" sx={{ flexGrow: 1 }}>
          Taskmodel system
        </Typography>
        <Typography variant="h6" sx={{ flexGrow: 1 }}></Typography>
        <Box>
          <Button component={Link} to="/" color="inherit">
            Home
          </Button>
          <Button component={Link} to="/list" color="inherit">
            Userlist
          </Button>
          <Button component={Link} to="/add-user" color="inherit">
            Add user
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
};
