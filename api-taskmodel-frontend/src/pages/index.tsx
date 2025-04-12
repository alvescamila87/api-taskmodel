import { Route, Routes } from "react-router-dom";
import { Menu } from "./Menu/Menu";
import { UserForm } from "./UserForm/UserForm";
import { UserPage } from "./UserPage";

export const Taskmodel = () => {
  return (
    <>
      <Menu />
      <Routes>
        <Route path="/" element={<UserPage />} />
        <Route path="/list" element={<UserPage />} />
        <Route path="/add-user" element={<UserForm />} />
      </Routes>
    </>
  );
};
