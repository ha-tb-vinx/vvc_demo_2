@echo off
rem =======================================
rem MSTB991060 TRE����IF�t�@�C���i�x�����@�}�X�^�j�쐬
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB991060 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
