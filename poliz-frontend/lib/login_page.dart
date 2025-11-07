import 'package:flutter/material.dart';
import 'styles/app_theme.dart';
import 'styles/styles.dart';
import 'dashboard_page.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});
  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final userCtrl = TextEditingController();
  final passCtrl = TextEditingController();
  bool obscured = true;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(children: [
        // top branding
        Align(
          alignment: Alignment.topCenter,
          child: Padding(
            padding: const EdgeInsets.only(top: 120),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: const [
                _GlowShield(),
                SizedBox(height: 18),
                Text('Poliz System', style: TextStyle(fontSize: 22, fontWeight: FontWeight.w700)),
                SizedBox(height: 8),
                Text('Secure Law Enforcement Portal', style: TextStyle(color: AppColors.textSecondary)),
              ],
            ),
          ),
        ),
        // form
        Align(
          alignment: Alignment.bottomCenter,
          child: Padding(
            padding: const EdgeInsets.fromLTRB(16, 0, 16, 28),
            child: DarkCard(
              padding: const EdgeInsets.fromLTRB(24, 22, 24, 24),
              child: ConstrainedBox(
                constraints: const BoxConstraints(maxWidth: 680),
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text('Username', style: TextStyle(fontWeight: FontWeight.w700, color: Colors.white,)),
                    gap8,
                    TextField(
                      controller: userCtrl,
                      style: const TextStyle(color: Colors.white),
                      decoration: const InputDecoration(
                          hintText: 'Enter your ID',
                          hintStyle: TextStyle(color: Colors.white70),
                      ),
                    ),
                    gap14(),
                    const Text('Password', style: TextStyle(fontWeight: FontWeight.w700,
                      color: Colors.white,)),
                    gap8,
                    TextField(
                      controller: passCtrl,
                      obscureText: obscured,
                      style: const TextStyle(color: Colors.white),
                      decoration: InputDecoration(
                        hintText: 'Enter your password',
                        hintStyle: const TextStyle(color: Colors.white70),
                        suffixIcon: IconButton(
                          icon: Icon(obscured ? Icons.visibility_off : Icons.visibility),
                          onPressed: () => setState(() => obscured = !obscured),
                        ),
                      ),
                    ),
                    gap20,
                    SizedBox(
                      height: 48,
                      width: double.infinity,
                      child: FilledButton(
                        onPressed: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(builder: (_) => const DashboardPage()),
                          );
                        },
                        child: const Text('Access System'),
                      ),
                    ),
                    gap16,
                    const Center(child: Text('Authorized personnel only', style: TextStyle(color: AppColors.textSecondary))),
                  ],
                ),
              ),
            ),
          ),
        ),
      ]),
    );
  }

  SizedBox gap14() => const SizedBox(height: 14);
}

class _GlowShield extends StatelessWidget {
  const _GlowShield();
  @override
  Widget build(BuildContext context) {
    return Container(
      width: 92,
      height: 92,
      decoration: const BoxDecoration(
        shape: BoxShape.circle,
        gradient: RadialGradient(colors: [Color(0xFF60A5FA), Colors.transparent], radius: .8),
      ),
      child: const Center(
        child: CircleAvatar(
          radius: 36,
          backgroundColor: Color(0xFF1E3A8A),
          child: Icon(Icons.shield_outlined, size: 38, color: Colors.white),
        ),
      ),
    );
  }
}
