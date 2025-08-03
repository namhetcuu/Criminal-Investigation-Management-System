'use client';

import { useParams } from 'next/navigation';

export default function VictimsPage() {
  const { role } = useParams();

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Victims - Role: {role}</h1>
      <table className="w-full border text-left">
        <thead className="bg-gray-100">
          <tr>
            <th className="p-2 border">Name</th>
            <th className="p-2 border">Age</th>
            <th className="p-2 border">Incident</th>
            <th className="p-2 border">Contact</th>
          </tr>
        </thead>
        <tbody>
          <tr className="hover:bg-gray-50">
            <td className="p-2 border">Jane Doe</td>
            <td className="p-2 border">34</td>
            <td className="p-2 border">Assault</td>
            <td className="p-2 border">0123456789</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}