import React from "react";

const cases = [
    {
        id: 1,
        title: "Case 001",
        status: "Open",
        assignedTo: "John Doe",
        createdAt: "2024-06-01",
    },
    {
        id: 2,
        title: "Case 002",
        status: "In Progress",
        assignedTo: "Jane Smith",
        createdAt: "2024-06-02",
    },
    {
        id: 3,
        title: "Case 003",
        status: "Closed",
        assignedTo: "Alice Johnson",
        createdAt: "2024-06-03",
    },
];

export default function CasesPage() {
    return (
        <main className="p-8">
            <div className="flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold">Cases</h1>
                <button className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition">
                    + New Case
                </button>
            </div>
            <div className="overflow-x-auto">
                <table className="min-w-full bg-white border rounded shadow">
                    <thead>
                        <tr>
                            <th className="px-4 py-2 border-b text-left">ID</th>
                            <th className="px-4 py-2 border-b text-left">Title</th>
                            <th className="px-4 py-2 border-b text-left">Status</th>
                            <th className="px-4 py-2 border-b text-left">Assigned To</th>
                            <th className="px-4 py-2 border-b text-left">Created At</th>
                            <th className="px-4 py-2 border-b"></th>
                        </tr>
                    </thead>
                    <tbody>
                        {cases.map((c) => (
                            <tr key={c.id} className="hover:bg-gray-50">
                                <td className="px-4 py-2 border-b">{c.id}</td>
                                <td className="px-4 py-2 border-b">{c.title}</td>
                                <td className="px-4 py-2 border-b">
                                    <span
                                        className={`px-2 py-1 rounded text-xs font-semibold ${c.status === "Open"
                                            ? "bg-green-100 text-green-800"
                                            : c.status === "In Progress"
                                                ? "bg-yellow-100 text-yellow-800"
                                                : "bg-gray-200 text-gray-700"
                                            }`}
                                    >
                                        {c.status}
                                    </span>
                                </td>
                                <td className="px-4 py-2 border-b">{c.assignedTo}</td>
                                <td className="px-4 py-2 border-b">{c.createdAt}</td>
                                <td className="px-4 py-2 border-b text-right">
                                    <button className="text-blue-600 hover:underline mr-2">
                                        View
                                    </button>
                                    <button className="text-gray-600 hover:underline">
                                        Edit
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </main>
    );
}