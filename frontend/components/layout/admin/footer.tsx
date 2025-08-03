import React from "react";

const AdminFooter = () => {
  return (
    <footer style={{ textAlign: "center", padding: "1rem 0", background: "#f5f5f5" }}>
      <small>&copy; {new Date().getFullYear()} Admin Dashboard. All rights reserved.</small>
    </footer>
  );
};

export default AdminFooter;