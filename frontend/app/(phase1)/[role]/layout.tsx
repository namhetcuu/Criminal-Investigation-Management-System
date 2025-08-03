export default function Phase1RoleLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="min-h-screen bg-gray-50">
      <header className="p-4 bg-white shadow"> {/* bạn có thể dùng Header riêng cho phase1 nếu muốn */}
        <h1 className="text-xl font-bold">Phase 1 </h1>
      </header>
      <main className="p-6">{children}</main>
    </div>
  );
}
