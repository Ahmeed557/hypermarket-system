import React, { useState, useEffect } from 'react';
import { User, Package, ShoppingCart, Truck, LogOut, AlertCircle, Plus, Search, Edit, Trash2 } from 'lucide-react';

const API_BASE = '/api';

export default function HypermarketSystem() {
  const [currentPage, setCurrentPage] = useState('login');
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [user, setUser] = useState(JSON.parse(localStorage.getItem('user') || 'null'));
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  useEffect(() => {
    if (token && user) {
      setCurrentPage('dashboard');
    }
  }, []);

  const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);

    const handleLogin = async () => {
      if (!username || !password) {
        setError('الرجاء إدخال اسم المستخدم وكلمة المرور');
        return;
      }

      setLoading(true);
      setError('');

      try {
        const res = await fetch(`${API_BASE}/users/login`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ username, password })
        });

        const data = await res.json();

        if (res.ok) {
          setToken(data.token);
          setUser({ username: data.username, role: data.role, email: data.email });
          localStorage.setItem('token', data.token);
          localStorage.setItem('user', JSON.stringify({ username: data.username, role: data.role }));
          setCurrentPage('dashboard');
          setSuccess('تم تسجيل الدخول بنجاح');
        } else {
          setError(data.message || 'خطأ في اسم المستخدم أو كلمة المرور');
        }
      } catch (err) {
        setError('فشل الاتصال بالخادم. تأكد من تشغيل Backend');
      } finally {
        setLoading(false);
      }
    };

    return (
      <div className="min-h-screen bg-gradient-to-br from-blue-600 to-purple-700 flex items-center justify-center p-4">
        <div className="bg-white rounded-2xl shadow-2xl p-8 w-full max-w-md">
          <div className="text-center mb-8">
            <div className="inline-block p-4 bg-blue-100 rounded-full mb-4">
              <ShoppingCart className="w-12 h-12 text-blue-600" />
            </div>
            <h1 className="text-3xl font-bold text-gray-800">نظام الهايبر ماركت</h1>
            <p className="text-gray-600 mt-2">تسجيل الدخول</p>
          </div>
          
          {error && (
            <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4 flex items-center">
              <AlertCircle className="w-5 h-5 ml-2" />
              {error}
            </div>
          )}

          {success && (
            <div className="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4">
              {success}
            </div>
          )}

          <div className="space-y-4">
            <div>
              <label className="block text-gray-700 mb-2 font-semibold">اسم المستخدم</label>
              <input
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                placeholder="أدخل اسم المستخدم"
                disabled={loading}
              />
            </div>
            <div>
              <label className="block text-gray-700 mb-2 font-semibold">كلمة المرور</label>
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                onKeyPress={(e) => e.key === 'Enter' && handleLogin()}
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                placeholder="أدخل كلمة المرور"
                disabled={loading}
              />
            </div>
            <button
              onClick={handleLogin}
              disabled={loading}
              className="w-full bg-blue-600 text-white py-3 rounded-lg hover:bg-blue-700 transition font-semibold disabled:bg-gray-400 disabled:cursor-not-allowed"
            >
              {loading ? 'جاري تسجيل الدخول...' : 'تسجيل الدخول'}
            </button>
          </div>
          <p className="text-center text-gray-600 mt-6">
            ليس لديك حساب؟{' '}
            <button onClick={() => setCurrentPage('register')} className="text-blue-600 hover:underline font-semibold">
              سجل الآن
            </button>
          </p>
        </div>
      </div>
    );
  };

  const RegisterPage = () => {
    const [formData, setFormData] = useState({
      username: '',
      email: '',
      password: '',
      fullName: '',
      phoneNumber: '',
      role: 'CASHIER'
    });
    const [loading, setLoading] = useState(false);

    const handleRegister = async () => {
      setLoading(true);
      setError('');

      try {
        const res = await fetch(`${API_BASE}/users/register`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(formData)
        });

        const data = await res.json();

        if (res.ok) {
          setSuccess('تم التسجيل بنجاح! جاري تسجيل الدخول...');
          setTimeout(() => {
            setToken(data.token);
            setUser({ username: data.username, role: data.role });
            localStorage.setItem('token', data.token);
            localStorage.setItem('user', JSON.stringify({ username: data.username, role: data.role }));
            setCurrentPage('dashboard');
          }, 1500);
        } else {
          setError(data.message || 'فشل التسجيل');
        }
      } catch (err) {
        setError('فشل الاتصال بالخادم');
      } finally {
        setLoading(false);
      }
    };

    return (
      <div className="min-h-screen bg-gradient-to-br from-purple-600 to-pink-700 flex items-center justify-center p-4">
        <div className="bg-white rounded-2xl shadow-2xl p-8 w-full max-w-2xl">
          <h2 className="text-3xl font-bold text-center mb-6">تسجيل مستخدم جديد</h2>
          
          {error && (
            <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
              {error}
            </div>
          )}
          {success && (
            <div className="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4">
              {success}
            </div>
          )}

          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-gray-700 mb-2 font-semibold">اسم المستخدم</label>
              <input
                type="text"
                value={formData.username}
                onChange={(e) => setFormData({...formData, username: e.target.value})}
                className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-purple-500"
                disabled={loading}
              />
            </div>
            <div>
              <label className="block text-gray-700 mb-2 font-semibold">البريد الإلكتروني</label>
              <input
                type="email"
                value={formData.email}
                onChange={(e) => setFormData({...formData, email: e.target.value})}
                className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-purple-500"
                disabled={loading}
              />
            </div>
            <div>
              <label className="block text-gray-700 mb-2 font-semibold">كلمة المرور</label>
              <input
                type="password"
                value={formData.password}
                onChange={(e) => setFormData({...formData, password: e.target.value})}
                className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-purple-500"
                disabled={loading}
              />
            </div>
            <div>
              <label className="block text-gray-700 mb-2 font-semibold">الاسم الكامل</label>
              <input
                type="text"
                value={formData.fullName}
                onChange={(e) => setFormData({...formData, fullName: e.target.value})}
                className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-purple-500"
                disabled={loading}
              />
            </div>
            <div>
              <label className="block text-gray-700 mb-2 font-semibold">رقم الهاتف</label>
              <input
                type="tel"
                value={formData.phoneNumber}
                onChange={(e) => setFormData({...formData, phoneNumber: e.target.value})}
                className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-purple-500"
                disabled={loading}
              />
            </div>
            <div>
              <label className="block text-gray-700 mb-2 font-semibold">الدور الوظيفي</label>
              <select
                value={formData.role}
                onChange={(e) => setFormData({...formData, role: e.target.value})}
                className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-purple-500"
                disabled={loading}
              >
                <option value="ADMIN">مدير</option>
                <option value="MANAGER">مدير فرع</option>
                <option value="CASHIER">كاشير</option>
                <option value="INVENTORY_STAFF">موظف مخزن</option>
              </select>
            </div>
            <div className="col-span-2 flex gap-4 mt-4">
              <button
                onClick={handleRegister}
                disabled={loading}
                className="flex-1 bg-purple-600 text-white py-3 rounded-lg hover:bg-purple-700 transition font-semibold disabled:bg-gray-400"
              >
                {loading ? 'جاري التسجيل...' : 'تسجيل'}
              </button>
              <button
                onClick={() => setCurrentPage('login')}
                disabled={loading}
                className="flex-1 bg-gray-300 text-gray-700 py-3 rounded-lg hover:bg-gray-400 transition font-semibold"
              >
                العودة للدخول
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  };

  const Dashboard = () => {
    const [products, setProducts] = useState([]);
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
      fetchData();
    }, []);

    const fetchData = async () => {
      try {
        const [productsRes, ordersRes] = await Promise.all([
          fetch(`${API_BASE}/products`, {
            headers: { 'Authorization': `Bearer ${token}` }
          }),
          fetch(`${API_BASE}/orders`, {
            headers: { 'Authorization': `Bearer ${token}` }
          })
        ]);

        if (productsRes.ok) {
          const productsData = await productsRes.json();
          setProducts(productsData);
        }

        if (ordersRes.ok) {
          const ordersData = await ordersRes.json();
          setOrders(ordersData);
        }
      } catch (err) {
        console.error('Failed to fetch data:', err);
        setError('فشل تحميل البيانات');
      } finally {
        setLoading(false);
      }
    };

    const logout = () => {
      setToken(null);
      setUser(null);
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      setCurrentPage('login');
    };

    return (
      <div className="min-h-screen bg-gray-100">
        <nav className="bg-blue-600 text-white shadow-lg">
          <div className="max-w-7xl mx-auto px-4 py-4 flex justify-between items-center">
            <div className="flex items-center gap-3">
              <ShoppingCart className="w-8 h-8" />
              <h1 className="text-2xl font-bold">نظام الهايبر ماركت</h1>
            </div>
            <div className="flex items-center gap-6">
              <div className="text-left">
                <p className="text-sm font-semibold">{user?.username}</p>
                <p className="text-xs opacity-90">{user?.role}</p>
              </div>
              <button
                onClick={logout}
                className="flex items-center gap-2 bg-red-500 px-4 py-2 rounded-lg hover:bg-red-600 transition"
              >
                <LogOut className="w-4 h-4" />
                خروج
              </button>
            </div>
          </div>
        </nav>

        <div className="max-w-7xl mx-auto p-6">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
            <div className="bg-white rounded-xl shadow-lg p-6 border-l-4 border-blue-500">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-gray-600 text-sm">إجمالي المنتجات</p>
                  <p className="text-3xl font-bold text-gray-800">{products.length}</p>
                </div>
                <Package className="w-12 h-12 text-blue-500 opacity-75" />
              </div>
            </div>
            <div className="bg-white rounded-xl shadow-lg p-6 border-l-4 border-green-500">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-gray-600 text-sm">إجمالي الطلبات</p>
                  <p className="text-3xl font-bold text-gray-800">{orders.length}</p>
                </div>
                <ShoppingCart className="w-12 h-12 text-green-500 opacity-75" />
              </div>
            </div>
            <div className="bg-white rounded-xl shadow-lg p-6 border-l-4 border-yellow-500">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-gray-600 text-sm">منتجات منخفضة</p>
                  <p className="text-3xl font-bold text-gray-800">
                    {products.filter(p => p.lowStock).length}
                  </p>
                </div>
                <AlertCircle className="w-12 h-12 text-yellow-500 opacity-75" />
              </div>
            </div>
            <div className="bg-white rounded-xl shadow-lg p-6 border-l-4 border-purple-500">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-gray-600 text-sm">المستخدمين</p>
                  <p className="text-3xl font-bold text-gray-800">1</p>
                </div>
                <User className="w-12 h-12 text-purple-500 opacity-75" />
              </div>
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
            <button
              onClick={() => setCurrentPage('products')}
              className="bg-gradient-to-r from-blue-500 to-blue-600 text-white p-6 rounded-xl shadow-lg hover:shadow-xl transition transform hover:-translate-y-1"
            >
              <Package className="w-10 h-10 mb-3 mx-auto" />
              <h3 className="text-xl font-bold">إدارة المنتجات</h3>
              <p className="text-sm opacity-90 mt-2">عرض وإضافة المنتجات</p>
            </button>
            <button
              onClick={() => setCurrentPage('orders')}
              className="bg-gradient-to-r from-green-500 to-green-600 text-white p-6 rounded-xl shadow-lg hover:shadow-xl transition transform hover:-translate-y-1"
            >
              <ShoppingCart className="w-10 h-10 mb-3 mx-auto" />
              <h3 className="text-xl font-bold">إدارة الطلبات</h3>
              <p className="text-sm opacity-90 mt-2">عرض وإنشاء الطلبات</p>
            </button>
            <button
              onClick={() => setCurrentPage('inventory')}
              className="bg-gradient-to-r from-purple-500 to-purple-600 text-white p-6 rounded-xl shadow-lg hover:shadow-xl transition transform hover:-translate-y-1"
            >
              <Truck className="w-10 h-10 mb-3 mx-auto" />
              <h3 className="text-xl font-bold">إدارة المخزون</h3>
              <p className="text-sm opacity-90 mt-2">إدارة الموردين والمخزون</p>
            </button>
          </div>

          <div className="bg-white rounded-xl shadow-lg p-6">
            <h2 className="text-2xl font-bold mb-4 text-gray-800">آخر المنتجات</h2>
            {loading ? (
              <p className="text-center text-gray-500 py-8">جاري التحميل...</p>
            ) : products.length === 0 ? (
              <p className="text-center text-gray-500 py-8">لا توجد منتجات بعد</p>
            ) : (
              <div className="overflow-x-auto">
                <table className="w-full">
                  <thead className="bg-gray-50">
                    <tr>
                      <th className="px-4 py-3 text-right text-sm font-semibold text-gray-700">الاسم</th>
                      <th className="px-4 py-3 text-right text-sm font-semibold text-gray-700">الفئة</th>
                      <th className="px-4 py-3 text-right text-sm font-semibold text-gray-700">السعر</th>
                      <th className="px-4 py-3 text-right text-sm font-semibold text-gray-700">الكمية</th>
                      <th className="px-4 py-3 text-right text-sm font-semibold text-gray-700">الحالة</th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-200">
                    {products.slice(0, 5).map(product => (
                      <tr key={product.id} className="hover:bg-gray-50">
                        <td className="px-4 py-3 text-gray-800 font-medium">{product.name}</td>
                        <td className="px-4 py-3 text-gray-600">{product.category}</td>
                        <td className="px-4 py-3 text-gray-800 font-semibold">{product.price} جنيه</td>
                        <td className="px-4 py-3 text-gray-600">{product.stockQuantity}</td>
                        <td className="px-4 py-3">
                          {product.lowStock ? (
                            <span className="px-3 py-1 bg-red-100 text-red-700 rounded-full text-xs font-semibold">
                              منخفض
                            </span>
                          ) : (
                            <span className="px-3 py-1 bg-green-100 text-green-700 rounded-full text-xs font-semibold">
                              متوفر
                            </span>
                          )}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        </div>
      </div>
    );
  };

  const renderPage = () => {
    switch (currentPage) {
      case 'login':
        return <LoginPage />;
      case 'register':
        return <RegisterPage />;
      case 'dashboard':
        return <Dashboard />;
      case 'products':
        return <Dashboard />;
      case 'orders':
        return <Dashboard />;
      case 'inventory':
        return <Dashboard />;
      default:
        return <LoginPage />;
    }
  };

  return (
    <div className="app">
      {renderPage()}
    </div>
  );
}